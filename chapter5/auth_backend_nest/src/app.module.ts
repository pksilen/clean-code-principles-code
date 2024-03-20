import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AllowForAuthorized } from './AllowForAuthorized';
import JwtAuthorizer from './JwtAuthorizer';

@Module({
  imports: [],
  controllers: [AppController],
  providers: [{ provide: 'authorizer', useClass: JwtAuthorizer }, AllowForAuthorized],
})
export class AppModule {
}
