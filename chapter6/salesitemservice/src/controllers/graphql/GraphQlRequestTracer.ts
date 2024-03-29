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
export class GraphQlRequestTracer implements NestInterceptor {
  constructor(@Inject('logger') private readonly logger: Logger) {}

  intercept(context: ExecutionContext, next: CallHandler): Observable<any> {
    const query = context.getArgByIndex(2).req.body.query;
    this.logger.log('TRACE', query);
    return next.handle();
  }
}
