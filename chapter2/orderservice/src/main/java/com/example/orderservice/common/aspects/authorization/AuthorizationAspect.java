package com.example.orderservice.common.aspects.authorization;

import com.example.orderservice.common.aspects.authorization.annotations.AllowForAuthorized;
import com.example.orderservice.common.aspects.authorization.annotations.AllowForSelf;
import com.example.orderservice.common.aspects.authorization.annotations.AllowForUserWithOneOfRoles;
import com.example.orderservice.common.authorizer.Authorizer;
import com.example.orderservice.common.utils.MethodParameterValueGetter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class AuthorizationAspect {
  private final Authorizer authorizer;

  @Autowired
  public AuthorizationAspect(final Authorizer authorizer) {
    this.authorizer = authorizer;
  }

  @Pointcut("""
    @annotation(com.example.orderservice.common.aspects.authorization.annotations.AllowForUserWithOneOfRoles) ||
    @annotation(com.example.orderservice.common.aspects.authorization.annotations.AllowForAuthorized) ||
    @annotation(com.example.orderservice.common.aspects.authorization.annotations.AllowForSelf)
    """)
  public void authAnnotationsPointcut() {
  }

  @Before("authAnnotationsPointcut()")
  public void authorizeRequest(JoinPoint joinPoint) {
    final var method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    final var maybeAuthHeader = MethodParameterValueGetter.<String>getParameterValue(joinPoint, "authHeader");

    for (final var methodAnnotation : method.getDeclaredAnnotations()) {
      if (methodAnnotation.annotationType().equals(AllowForUserWithOneOfRoles.class)) {
        final var roles = List.of(((AllowForUserWithOneOfRoles) methodAnnotation).value());
        authorizer.authorizeIfUserHasOneOfRoles(roles, maybeAuthHeader);
      } else if (methodAnnotation.annotationType().equals(AllowForAuthorized.class)) {
        authorizer.authorize(maybeAuthHeader);
      } else if (methodAnnotation.annotationType().equals(AllowForSelf.class)) {
        final var maybeUserId = MethodParameterValueGetter.<String>getParameterValue(joinPoint, "userId");
        authorizer.authorizeForSelf(maybeUserId, maybeAuthHeader);
      }
    }
  }
}
