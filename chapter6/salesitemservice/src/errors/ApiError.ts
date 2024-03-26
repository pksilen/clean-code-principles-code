import { getStackTrace } from '../utils/utils';

export default class ApiError extends Error {
  constructor(
    private readonly _statusCode: number,
    private readonly statusText: string,
    private readonly errorMessage: string,
    private readonly errorCode: string | undefined,
    private readonly errorDescription?: string,
    private readonly cause?: Error,
  ) {
    super();
  }

  toResponseObject(request: Request) {
    return {
      statusCode: this._statusCode,
      statusText: this.statusText,
      timestamp: new Date().toISOString(),
      endpoint: `${request.method} ${request.url}`,
      errorCode: this.errorCode,
      errorMessage: this.errorMessage,
      errorDescription: this.errorDescription,
      // getStackTrace returns stack trace only
      // when environment is not production
      // otherwise it returns 'undefined'
      stackTrace: getStackTrace(this.cause),
    };
  }

  get statusCode(): number {
    return this._statusCode;
  }
}