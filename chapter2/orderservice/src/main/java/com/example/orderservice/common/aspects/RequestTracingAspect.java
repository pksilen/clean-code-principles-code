package com.example.orderservice.common.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestTracingAspect {
  @Before("@annotation(TraceRequest)")
  public void traceRequest(JoinPoint joinPoint) {
    System.out.println("Aspect: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    // logger.log(LogLevel.TRACE, httpRequest.getMethod() + " " + httpRequest.getRequestURI() + " " + requestBody);
  }
}
