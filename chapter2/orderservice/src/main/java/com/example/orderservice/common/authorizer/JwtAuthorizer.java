package com.example.orderservice.common.authorizer;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

// @Service
public class JwtAuthorizer implements Authorizer {
  private final String issuerUrl;
  private final String jwksUrl;
  private final String getUsersUrl;

  public JwtAuthorizer() {
    // https:{your-IAM-service-domain-name}
    this.issuerUrl = System.getenv("ISSUER_URL");
    // https:{your-IAM-service-domain-name}/.well-known/jwks.json
    this.jwksUrl = System.getenv("JWKS_URL");
    this.getUsersUrl = System.getenv("GET_USERS_URL");
  }

  @Override
  public void authorize(final Optional<String> authHeader) {
    this.tryGetClaims(authHeader);
  }

  @Override
  public void authorizeForSelf(final Optional<String> maybeUserId, final Optional<String> maybeAuthHeader) {
    final var userIdInJwt = this.getUserId(maybeAuthHeader);

    if (maybeUserId.isEmpty() || !maybeUserId.get().equals(userIdInJwt)) {
      throw new UnauthorizedError();
    }
  }

  @Override
  public void authorizeIfUserHasOneOfRoles(final List<String> allowedRoles, final Optional<String> maybeAuthHeader) {
    final var claims = this.tryGetClaims(maybeAuthHeader);
    final var roles = List.of(claims.get("roles").asArray(String.class));
    final var isAuthorized = allowedRoles.stream().anyMatch(roles::contains);

    if (!isAuthorized) {
      throw new UnauthorizedError();
    }
  }

  @Override
  public String getUserId(final Optional<String> maybeAuthHeader) {
    final var claims = this.tryGetClaims(maybeAuthHeader);
    final var getUserUrl = getUsersUrl + "?sub=" + claims.get("sub").asString() + "&fields=id";

    try {
      final var users = new RestTemplate().getForObject(getUserUrl, User[].class);

      if (users != null && users.length == 1 && users[0].getId() != null) {
        return users[0].getId();
      } else {
        throw new UnauthorizedError();
      }
    } catch (final RestClientException error) {
      // Log error details
      throw new IamError();
    }
  }

  private Map<String, Claim> tryGetClaims(final Optional<String> maybeAuthHeader) {
    final var maybeClaims = getMaybeJwt(maybeAuthHeader).map(jwt -> {
      try {
        final var jwkProvider = new JwkProviderBuilder(jwksUrl).cached(10, 48, TimeUnit.HOURS).build();
        final var decodedJwt = JWT.decode(jwt);
        final var jwk = jwkProvider.get(decodedJwt.getKeyId());
        final var algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
        final var jwtVerifier = JWT.require(algorithm).withIssuer(issuerUrl).build();
        final var verifiedJWT = jwtVerifier.verify(jwt);
        return verifiedJWT.getClaims();
      } catch (final JwkException error) {
        throw new IamError();
      } catch (final JWTVerificationException error) {
        throw new UnauthorizedError();
      }
    });

    return maybeClaims.orElse(new HashMap<>());
  }

  private Optional<String> getMaybeJwt(final Optional<String> maybeAuthHeader) {
    return maybeAuthHeader.map(authHeader -> {
      final var authHeadersParts = authHeader.split("Bearer ");
      return authHeadersParts.length == 2 ? authHeadersParts[1] : null;
    });
  }
}
