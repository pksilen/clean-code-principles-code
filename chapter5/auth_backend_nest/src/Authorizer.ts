export default interface Authorizer {
  authorize(authHeader: string | undefined): Promise<void>;

  authorizeForSelf(
    userId: number,
    authHeader: string | undefined
  ): Promise<void>;

  authorizeIfUserHasOneOfRoles(
    allowedRoles: string[],
    authHeader: string | undefined
  ): Promise<void>;

  getUserId(authHeader: string | undefined): Promise<number>;
}
