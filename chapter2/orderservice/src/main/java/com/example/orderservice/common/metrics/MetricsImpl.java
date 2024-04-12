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
}
