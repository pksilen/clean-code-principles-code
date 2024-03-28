import {
  CallHandler,
  ExecutionContext,
  Inject,
  Injectable,
  NestInterceptor,
} from '@nestjs/common';
import { Observable } from 'rxjs';
import { Logger } from '../common/logger/Logger';

@Injectable()
export class RequestTracer implements NestInterceptor {
  constructor(@Inject('logger') private readonly logger: Logger) {}

  intercept(context: ExecutionContext, next: CallHandler): Observable<any> {
    const httpArgumentsHost = context.switchToHttp();
    const request = httpArgumentsHost.getRequest();

    this.logger.log(
      'TRACE',
      `${request.method} ${request.url} ${JSON.stringify(request.body)}`,
    );

    return next.handle();
  }
}
