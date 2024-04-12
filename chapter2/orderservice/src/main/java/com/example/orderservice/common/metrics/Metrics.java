package com.example.orderservice.common.metrics;

import org.springframework.stereotype.Service;

@Service
public interface Metrics {
  void incrementRequestCount(final String endpoint);

  long getRequestCount();
}
