package com.example.orderservice.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundError extends RuntimeException {
  public EntityNotFoundError(final String entityType, final String id) {
    super(entityType + " entity not found with id " + id);
  }
}
