import { Authorizer } from './Authorizer';
import { Injectable } from '@nestjs/common';

@Injectable()
class FakeAuthorizer implements Authorizer {
  authorizeIfUserHasOneOfRoles(roles: string[]): void {
    console.log('Authorized user with one of the roles: ' + roles.toString());
  }
}

export const authorizer = new FakeAuthorizer();
