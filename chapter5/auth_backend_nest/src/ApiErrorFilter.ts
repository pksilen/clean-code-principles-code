import { ArgumentsHost, Catch, ExceptionFilter } from '@nestjs/common';
import { Request, Response } from 'express';
import ApiError from './ApiError';

@Catch(ApiError)
export default class ApiErrorFilter implements ExceptionFilter {
  catch(error: ApiError, host: ArgumentsHost) {
    console.log('ApiError');

    const ctx = host.switchToHttp();
    const response = ctx.getResponse<Response>();
    const request = ctx.getRequest<Request>();

    response
      .status(500)
      .json({
        statusCode: 500,
        timestamp: new Date().toISOString(),
        path: request.url,
      });
  }
}
