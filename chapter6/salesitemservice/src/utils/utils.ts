// Define an interface for the error dictionary structure
interface ErrorResponse {
  statusCode: number;
  statusText: string;
  endpoint: string;
  timestamp: string;
  errorCode: string;
  errorMessage: string;
  errorDescription: string;
  stackTrace: string;
}

const statusCodeToText: Record<number, string> = {
  400: 'Bad Request',
  500: 'Internal Server Error',
};

export function createErrorResponse(
  error: Error,
  statusCode: number,
  errorCode: string,
  requestOrEndpointString: any,
): ErrorResponse {
  const errorMessage = errorCode
    .split(/(?=[A-Z])/)
    .join(' ')
    .toLowerCase();

  let endpoint: string;

  if (typeof requestOrEndpointString === 'string') {
    endpoint = requestOrEndpointString;
  } else {
    endpoint = `${requestOrEndpointString.method} ${requestOrEndpointString.url}`;
  }

  return {
    statusCode: statusCode,
    statusText: statusCodeToText[statusCode],
    endpoint: endpoint,
    timestamp: new Date().toISOString(),
    errorCode: errorCode,
    errorMessage: errorMessage,
    errorDescription: error.toString(),
    stackTrace: getStackTrace(error),
  };
}

export function getStackTrace(error: Error) {
  return process.env.ENV !== 'production' ? error.stack : undefined;
}
