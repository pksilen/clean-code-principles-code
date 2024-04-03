import {
  CallHandler,
  ExecutionContext,
  Inject,
  Injectable,
  NestInterceptor,
} from '@nestjs/common';
import { Observable } from 'rxjs';
import { Metrics } from '../common/metrics/Metrics';

@Injectable()
export class RequestCounter implements NestInterceptor {
  constructor(@Inject('metrics') private readonly metrics: Metrics) {}

  intercept(context: ExecutionContext, next: CallHandler): Observable<any> {
    const httpArgumentsHost = context.switchToHttp();
    const request = httpArgumentsHost.getRequest();

    this.metrics.incrementRequestCounter(
      `${request.method} ${request.url.split('/')[1]}`,
    );

    return next.handle();
  }
}
