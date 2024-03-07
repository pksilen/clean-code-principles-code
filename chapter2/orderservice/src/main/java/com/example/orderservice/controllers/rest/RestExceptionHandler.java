package com.example.orderservice.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
  // This is just an example exception handler for any exception for demonstration purposes
  // You should map thrown errors/exceptions to proper JSON error messages with appropriate status
  // code
  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleAnyException(final Exception exception) {
    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body("Customer handler message");
  }
}
