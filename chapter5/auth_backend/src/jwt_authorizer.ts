import _ from 'lodash';
import { decode, verify } from 'jsonwebtoken';
import { fetch } from 'fetch-h2';
import jwks from 'jwks-rsa';
import throwError from './throwError';
import throwException from './throwException';
import AbstractAuthorizer from './AbstractAuthorizer';

class JwtAuthorizer extends AbstractAuthorizer {
  private readonly oidcConfigUrl: string;
  private readonly rolesClaimPath: string;
  private readonly getUsersUrl: string;
  private jwksClient: any;

  constructor() {
    super();
    // URL for OpenId Connect configuration endpoint in the IAM system
    this.oidcConfigUrl =
      process.env.OIDC_CONFIG_URL ??
      throwException('OIDC_CONFIG_URL is not defined');

    // With Keycloak you can use e.g., realm_access.roles
    this.rolesClaimPath = process.env.JWT_ROLES_CLAIM_PATH ??
      throwException('JWT_ROLES_CLAIM_PATH is not defined');

    // This is the URL where you can fetch the user id for a
    // specific 'sub' claim value in the access token
    // For example: http://localhost:8082/user-service/users
    this.getUsersUrl =
      process.env.GET_USERS_URL ??
      throwException('GET_USERS_URL is not defined');
  }

  async authorize(authHeader: string | undefined): Promise<void> {
    await this.tryGetClaims(authHeader);
  }

  // Authorize a user for him/herself
  async authorizeForSelf(
    userId: number,
    authHeader: string | undefined
  ): Promise<void> {
    const userIdInJwt = await this.getUserId(authHeader);

    if (userId !== userIdInJwt) {
      throwError(JwtAuthorizer.UnauthorizedError);
    }
  }

  async authorizeIfUserHasOneOfRoles(
    allowedRoles: string[],
    authHeader: string | undefined
  ): Promise<void> {
    const claims = await this.tryGetClaims(authHeader);
    const roles = _.get(claims, this.rolesClaimPath, []);

    const isAuthorized = allowedRoles.some((allowedRole) =>
      roles.includes(allowedRole));

    if (!isAuthorized) {
      throwError(JwtAuthorizer.UnauthorizedError);
    }
  }

  async getUserId(authHeader: string | undefined): Promise<number> {
    const claims = await this.tryGetClaims(authHeader);
    const getUsersUrl = this.getUsersUrl + `?sub=${claims?.sub}&fields=id`;
    let users;

    try {
      const usersResponse = await fetch(getUsersUrl);
      users = await usersResponse.json();
    } catch (error) {
      // Log error details
      throw new JwtAuthorizer.IamError();
    }

    return users?.[0]?.id ?? throwError(JwtAuthorizer.UnauthorizedError);
  }

  private async tryGetClaims(authHeader: string | undefined): Promise<any> {
    if (!authHeader) {
      throwError(JwtAuthorizer.UnauthenticatedError);
    }

    let oidcConfig;

    try {
      const oidcConfigResponse = await fetch(this.oidcConfigUrl);
      oidcConfig = await oidcConfigResponse.json();
    } catch (error) {
      // Log error details
      throwError(JwtAuthorizer.IamError);
    }

    if (!this.jwksClient) {
      this.jwksClient = jwks({ jwksUri: oidcConfig?.jwks_uri });
    }

    const jwt = authHeader?.split('Bearer ').pop() ?? '';

    try {
      const decodedJwt = decode(jwt, { complete: true });
      const kid = decodedJwt?.header?.kid;
      const signingKey = await this.jwksClient.getSigningKey(kid);
      return verify(jwt, signingKey.getPublicKey());
    } catch (error) {
      throwError(JwtAuthorizer.UnauthorizedError);
    }
  }
}

export const authorizer = new JwtAuthorizer();
