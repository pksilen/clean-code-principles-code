import { ArgumentsHost, Catch, ExceptionFilter } from '@nestjs/common';
import { createErrorResponse } from '../utils/utils';

@Catch()
export class ErrorFilter implements ExceptionFilter {
  catch(error: Error, host: ArgumentsHost) {
    const context = host.switchToHttp();
    const response = context.getResponse();
    const request = context.getRequest();

    // Log error

    // Increment 'request_failures' counter by one
    // with labels:
    // api_endpoint=f'{request.method} {request.url}'
    // status_code=500
    // error_code='UnspecifiedError'

    response
      .status(500)
      .json(
        createErrorResponse(error, 500, 'UnspecifiedInternalError', request),
      );
  }
}
