package com.example.orderservice.common.metrics;

public class Counter {
  private long count = 0;

  public void increment() {
    count++;
  }

  public long getCount() {
    return count;
  }
}
