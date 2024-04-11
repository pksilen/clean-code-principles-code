package com.example.orderservice.errors;

public class DatabaseError extends OrderServiceError {
  public DatabaseError(final Throwable throwable) {
    super(throwable);
  }
}
