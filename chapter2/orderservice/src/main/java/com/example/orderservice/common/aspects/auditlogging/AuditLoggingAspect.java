package com.example.orderservice.common.aspects.auditlogging;

import com.example.orderservice.common.logger.audit.AuditLogger;
import com.example.orderservice.common.utils.MethodParameterValueGetter;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class AuditLoggingAspect {
  private final AuditLogger auditLogger;

  @Autowired
  public AuditLoggingAspect(final AuditLogger auditLogger) {
    this.auditLogger = auditLogger;
  }

  @Before("@annotation(com.example.orderservice.common.aspects.auditlogging.AuditLog)")
  public void auditLog(JoinPoint joinPoint) {
    var auditLogMessage = MethodParameterValueGetter.<String>getParameterValue(joinPoint, "auditLogMessage");

    if (auditLogMessage.isEmpty()) {
      final var maybeHttpRequest = MethodParameterValueGetter.<HttpServletRequest>getParameterValue(joinPoint, "request");

      if (maybeHttpRequest.isPresent()) {
        final var httpRequest = maybeHttpRequest.get();

        auditLogMessage = Optional.of(
          httpRequest.getMethod() + " " + httpRequest.getRequestURI() +
            " is accessed from " + httpRequest.getRemoteAddr());
      }
    }

    auditLogger.log(auditLogMessage.orElse(""));
  }
}
