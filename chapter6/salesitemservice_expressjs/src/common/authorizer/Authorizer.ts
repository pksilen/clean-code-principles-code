export interface Authorizer {
  // ...
  authorizeIfUserHasOneOfRoles(
    roles: string[],
    authHeader: string | undefined,
  ): void;
}
