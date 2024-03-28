import {
  ArgumentsHost,
  BadRequestException,
  Catch,
  ExceptionFilter,
} from '@nestjs/common';
import { createErrorResponse } from '../common/utils/utils';
import { ApolloError } from 'apollo-server-express';

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

    if (process.env.CONTROLLER_TYPE === 'graphql') {
      return new ApolloError(
        error.message,
        undefined,
        createErrorResponse(error, 400, 'RequestValidationError', undefined),
      );
    }

    response
      .status(400)
      .json(createErrorResponse(error, 400, 'RequestValidationError', request));
  }
}
