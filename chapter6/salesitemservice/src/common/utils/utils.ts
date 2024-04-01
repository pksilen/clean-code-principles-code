// Define an interface for the error dictionary structure
import { HttpException } from '@nestjs/common';
import { status } from '@grpc/grpc-js';
import ApiError from '../../errors/ApiError';
import { ValidationError } from 'class-validator';

interface ErrorResponse {
  statusCode: number;
  statusText: string;
  endpoint: string;
  timestamp: string;
  errorCode: string;
  errorMessage: string;
  errorDescription: string;
  stackTrace: string | undefined;
}

const statusCodeToText: Record<number, string> = {
  400: 'Bad Request',
  500: 'Internal Server Error',
};

export function createErrorResponse(
  error: Error,
  statusCode: number,
  errorCode: string,
  requestOrEndpoint: any,
): ErrorResponse {
  let errorMessage = errorCode
    .split(/(?=[A-Z])/)
    .join(' ')
    .toLowerCase();

  errorMessage = errorMessage.charAt(0).toUpperCase() + errorMessage.slice(1);

  const endpoint =
    requestOrEndpoint?.method && requestOrEndpoint?.url
      ? `${requestOrEndpoint.method} ${requestOrEndpoint.url}`
      : requestOrEndpoint;

  return {
    statusCode: statusCode,
    statusText: statusCodeToText[statusCode],
    endpoint: endpoint,
    timestamp: new Date().toISOString(),
    errorCode: errorCode,
    errorMessage: errorMessage,
    errorDescription:
      error instanceof HttpException
        ? (error.getResponse() as any)?.message
        : error.toString(),
    stackTrace: getStackTrace(error),
  };
}

export function createGrpcErrorResponse(endpoint: string, error: Error) {
  let code;
  let details;

  if (error instanceof ApiError) {
    code = mapHttpStatusCodeToGrpcStatusCode(error.statusCode);
    details = error.toResponse(endpoint);
  } else if (Array.isArray(error) && error?.[0] instanceof ValidationError) {
    code = status.INVALID_ARGUMENT;
    details = error[0].toString();
  } else {
    code = status.INTERNAL;
    details = createErrorResponse(
      error,
      500,
      'UnspecifiedInternalError',
      endpoint,
    );
  }

  return {
    code,
    details: typeof details === 'object' ? JSON.stringify(details) : details,
  };
}

export function getStackTrace(error?: Error) {
  return process.env.ENV !== 'production' ? error?.stack : undefined;
}

export function getDbConnProperties() {
  const databaseUrl = process.env.DATABASE_URL;

  if (!databaseUrl) {
    throw new Error('DATABASE_URL environment variable is not set');
  }

  const [, , authAndHost, path] = databaseUrl.split('/');
  const [userAndPassword, hostAndPort] = authAndHost.split('@');
  const [user, password] = userAndPassword.split(':');
  const [host, portString] = hostAndPort.split(':');
  const port = parseInt(portString, 10);
  const database = path;
  return { user, password, host, port, database };
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
function mapHttpStatusCodeToGrpcStatusCode(httpStatusCode: number) {
  // Map HTTP status code here to
  // respective gRPC status code ...
  // Mapping info is available here:
  // https://cloud.google.com/apis/design/errors#error_model
  return status.INTERNAL;
}
