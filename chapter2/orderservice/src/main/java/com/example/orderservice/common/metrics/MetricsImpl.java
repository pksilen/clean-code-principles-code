package com.example.orderservice.common.metrics;

import org.springframework.stereotype.Service;

@Service
public class MetricsImpl implements Metrics {
  private final Counter requestCounter = new Counter();

  @Override
  public void incrementRequestCount(String endpoint) {
    requestCounter.increment();
    System.out.println("Request count for endpoint " + endpoint + " is " + requestCounter.getCount());
  }

  @Override
  public long getRequestCount() {
    return requestCounter.getCount();
  }

  @Override
  public void addRequestDurationInNs(String endpoint, long requestDurationInNs) {
    // Implement using a histogram, e.g. using Prometheus Java client
    System.out.println("Request duration of " + (requestDurationInNs / (1000.0 * 1000.0)) + " ms for endpoint " + endpoint + " added to histogram");
  }
}
