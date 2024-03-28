// Define an interface for the error dictionary structure
import { HttpException } from '@nestjs/common';

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
