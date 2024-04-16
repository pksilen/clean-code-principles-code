import logger from "../common/logger/StdOutLogger";

export default function requestTracingMiddleware(request, response, next) {
  logger.log(
    "TRACE",
    `${request.method} ${request.url} ${JSON.stringify(request.body)}`,
  );

  next();
}
