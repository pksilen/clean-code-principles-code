package com.example.orderservice.common.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Optional;

public final class MethodParameterValueGetter {
  public static <T> Optional<T> getParameterValue(
    final JoinPoint joinPoint,
    final String parameterName
  ) {
    final var methodParameters = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameters();
    T parameterValue = null;

    for (int index = 0; index < methodParameters.length; index++) {
      if (methodParameters[index].getName().equals(parameterName)) {
        parameterValue = (T) joinPoint.getArgs()[index];
      }
    }

    return Optional.ofNullable(parameterValue);
  }
}
