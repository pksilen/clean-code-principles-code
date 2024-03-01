package com.example.salesitemservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundError extends RuntimeException {
    EntityNotFoundError(final String entityType, final long id) {
        super(entityType + " entity not found with id " + id);
    }
}
