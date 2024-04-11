package com.example.orderservice.errors;

public class OrderServiceError extends RuntimeException {
  public OrderServiceError(final String message) {
    super(message);
  }

  public OrderServiceError(final Throwable throwable) {
    super(throwable);
  }
}
