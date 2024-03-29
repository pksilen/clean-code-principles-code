import {
  CallHandler,
  ExecutionContext,
  Inject,
  Injectable,
  NestInterceptor,
} from '@nestjs/common';
import { Observable } from 'rxjs';
import { Logger } from '../../common/logger/Logger';

@Injectable()
export class WebSocketRequestTracer implements NestInterceptor {
  constructor(@Inject('logger') private readonly logger: Logger) {}

  intercept(context: ExecutionContext, next: CallHandler): Observable<any> {
    const wsArgumentsHost = context.switchToWs();
    const event = wsArgumentsHost.getPattern();
    const data = wsArgumentsHost.getData();
    this.logger.log(
      'TRACE',
      `Event ${event} with data: ${JSON.stringify(data)}`,
    );
    return next.handle();
  }
}
