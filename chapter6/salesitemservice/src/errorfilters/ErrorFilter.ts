import { ArgumentsHost, Catch, ExceptionFilter } from '@nestjs/common';
import { createErrorResponse } from '../utils/utils';
import { ApolloError } from 'apollo-server-express';

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

    if (process.env.CONTROLLER_TYPE === 'graphql') {
      return new ApolloError(
        error.message,
        undefined,
        createErrorResponse(error, 500, 'UnspecifiedInternalError', undefined),
      );
    }

    response
      .status(500)
      .json(
        createErrorResponse(error, 500, 'UnspecifiedInternalError', request),
      );
  }
}
