import { CanActivate, ExecutionContext, Inject, Injectable } from '@nestjs/common';
import Authorizer from './Authorizer';

@Injectable()
export class AllowForAuthorized implements CanActivate {
  constructor(@Inject('authorizer') private authorizer: Authorizer) {
  }

  async canActivate(context: ExecutionContext): Promise<boolean> {
    const request = context.switchToHttp().getRequest();
    await this.authorizer.authorize(request.headers.authorization);
    console.log('Authorized');
    return true;
  }
}
