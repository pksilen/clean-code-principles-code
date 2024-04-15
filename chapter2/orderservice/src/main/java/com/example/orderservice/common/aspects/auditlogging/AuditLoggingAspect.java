package com.example.orderservice.common.aspects.auditlogging;

import com.example.orderservice.common.utils.MethodParameterValueGetter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditLoggingAspect {
  @Before("@annotation(com.example.orderservice.common.aspects.auditlogging.AuditLog)")
  public void auditLog(JoinPoint joinPoint) {
    final var auditLogMessage = MethodParameterValueGetter.<String>getParameterValue(joinPoint, "auditLogMessage");
    // Contact a remote audit logging service via a remote API call here ...
    System.out.println(auditLogMessage.orElse(""));
  }
}
