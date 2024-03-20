import Authorizer from './Authorizer';
import ApiError from './ApiError';

export default abstract class AbstractAuthorizer implements Authorizer {
  // An example implementation for 'ApiError' class is given
  // in API principles chapter
  static readonly UnauthenticatedError = class extends ApiError {
  };

  static readonly UnauthorizedError = class extends ApiError {
  };

  static readonly IamError = class extends ApiError {
  };

  abstract authorize(authHeader: string | undefined): Promise<void>;

  abstract authorizeForSelf(
    userId: number,
    authHeader: string | undefined
  ): Promise<void>;

  abstract authorizeIfUserHasOneOfRoles(
    allowedRoles: string[],
    authHeader: string | undefined
  ): Promise<void>;

  abstract getUserId(authHeader: string | undefined): Promise<number>;
}
