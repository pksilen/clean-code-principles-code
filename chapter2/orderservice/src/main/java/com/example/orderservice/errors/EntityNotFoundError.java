package com.example.orderservice.errors;

public class EntityNotFoundError extends OrderServiceError {
  public EntityNotFoundError(final String entityType, final String id) {
    super(entityType + " entity not found with id " + id);
  }
}
