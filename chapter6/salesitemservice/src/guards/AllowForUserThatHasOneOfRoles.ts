import { CanActivate, ExecutionContext, Injectable } from '@nestjs/common';
import { Observable } from 'rxjs';
import { Authorizer } from '../common/authorizer/Authorizer';

@Injectable()
export class AllowForUserThatHasOneOfRoles implements CanActivate {
  constructor(
    private readonly roles: string[],
    private readonly authorizer: Authorizer,
  ) {}

  canActivate(
    context: ExecutionContext,
  ): boolean | Promise<boolean> | Observable<boolean> {
    const request = context.switchToHttp().getRequest();

    this.authorizer.authorizeIfUserHasOneOfRoles(
      this.roles,
      request.headers.authorization,
    );

    return true;
  }
}
