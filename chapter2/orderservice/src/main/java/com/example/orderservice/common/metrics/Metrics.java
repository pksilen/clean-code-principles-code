package com.example.orderservice.common.metrics;

import org.springframework.stereotype.Service;

@Service
public interface Metrics {
  void incrementRequestCount(final String endpoint);

  long getRequestCount();

  void addRequestDurationInNs(final String endpoint, final long requestDurationInNs);
}
