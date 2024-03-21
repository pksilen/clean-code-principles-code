import { ArgumentsHost, Catch, ExceptionFilter } from '@nestjs/common';
import SalesItemServiceError from '../errors/SalesItemServiceError';

@Catch(SalesItemServiceError)
export default class SalesItemServiceErrorFilter implements ExceptionFilter {
  catch(error: SalesItemServiceError, host: ArgumentsHost) {
    const context = host.switchToHttp();
    const response = context.getResponse();
    const request = context.getRequest();

    // Log error.cause at least always
    // when error.status_code >= 500

    // Increment 'request_failures' counter by one
    // with three labels:
    // api_endpoint=`${request.method} ${request.url}`
    // status_code=error.statusCode
    // error_code=error.code

    response.status(error.statusCode).json(error.toResponseObject(request));
  }
}
