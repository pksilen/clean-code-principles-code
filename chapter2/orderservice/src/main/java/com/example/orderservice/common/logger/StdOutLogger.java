package com.example.orderservice.common.logger;

import org.springframework.stereotype.Service;

@Service
public class StdOutLogger implements Logger {
  @Override
  public void log(LogLevel logLevel, String message) {
    System.out.println(logLevel.name() + ": " + message); // NOSONAR
  }
}
