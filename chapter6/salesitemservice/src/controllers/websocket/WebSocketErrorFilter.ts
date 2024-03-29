import { ArgumentsHost, Catch } from '@nestjs/common';
import { BaseWsExceptionFilter } from '@nestjs/websockets';
import { ValidationError } from 'class-validator';

@Catch()
export class WebSocketErrorFilter extends BaseWsExceptionFilter {
  catch(error: Error, host: ArgumentsHost) {
    const client = host.switchToWs().getClient();
    let message = error.message;

    if (Array.isArray(error) && error?.[0] instanceof ValidationError) {
      message = error[0].toString();
    }

    const errorResponse = {
      status: 'error',
      message,
    };

    client.send(JSON.stringify(errorResponse));
  }
}
