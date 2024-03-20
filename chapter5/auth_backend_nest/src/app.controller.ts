import { Controller, Post, Req, UseGuards } from '@nestjs/common';
import { AllowForAuthorized } from './AllowForAuthorized';

@Controller()
export class AppController {
  constructor() {
  }

  @UseGuards(AllowForAuthorized)
  @Post('/api/messaging-service/messages')
  createMessage(@Req() request: Request): void {
    console.log('Message created');
  }
}
