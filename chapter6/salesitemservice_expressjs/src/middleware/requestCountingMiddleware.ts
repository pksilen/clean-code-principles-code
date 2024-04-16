import metrics from "../common/metrics/MetricsImpl";

export default function requestCountingMiddleware(request, response, next) {
  metrics.incrementRequestCounter(
    `${request.method} ${request.url.split("/")[1]}`,
  );

  next();
}
