package com.example.orderservice.common.logger.audit;

import org.springframework.stereotype.Service;

@Service
public class FakeAuditLogger implements AuditLogger {
  @Override
  public void log(final String message) {
    // In a real audit logger, contact a remote audit logging service via a remote API call here ...
    System.out.println(message);
  }
}
