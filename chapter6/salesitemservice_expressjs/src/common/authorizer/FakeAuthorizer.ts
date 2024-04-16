import { Authorizer } from "./Authorizer";

export default class FakeAuthorizer implements Authorizer {
  authorizeIfUserHasOneOfRoles(roles: string[]): void {
    console.log("Authorized user with one of the roles: " + roles.toString());
  }
}
