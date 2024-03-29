import {
  CallHandler,
  ExecutionContext,
  Inject,
  Injectable,
  NestInterceptor,
} from '@nestjs/common';
import { Observable } from 'rxjs';
import { AuditLoggingService } from '../common/logger/audit/AuditLoggingService';

@Injectable()
export class AuditLogger implements NestInterceptor {
  constructor(
    @Inject('auditLoggingService')
    private readonly auditLogger: AuditLoggingService,
  ) {}

  intercept(context: ExecutionContext, next: CallHandler): Observable<any> {
    const httpArgumentsHost = context.switchToHttp();
    const request = httpArgumentsHost.getRequest();

    this.auditLogger.log(
      `Endpoint ${request.method} ${request.url} accessed from ${request.ip}`,
    );

    return next.handle();
  }
}
