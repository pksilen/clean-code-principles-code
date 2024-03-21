import {
  ArgumentsHost,
  BadRequestException,
  Catch,
  ExceptionFilter,
} from '@nestjs/common';
import { createErrorResponse } from '../utils/utils';

@Catch(BadRequestException)
export class ValidationErrorFilter implements ExceptionFilter {
  catch(error: BadRequestException, host: ArgumentsHost) {
    const context = host.switchToHttp();
    const response = context.getResponse();
    const request = context.getRequest();

    // Audit log

    // Increment 'request_failures' counter by one
    // with three labels:
    // api_endpoint=`${request.method} ${request.url}`
    // status_code=400
    // errorCode="RequestValidationError"

    response
      .status(400)
      .json(createErrorResponse(error, 400, 'RequestValidationError', request));
  }
}
