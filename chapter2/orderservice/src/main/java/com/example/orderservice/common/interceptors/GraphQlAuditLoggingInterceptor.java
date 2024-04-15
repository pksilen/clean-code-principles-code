package com.example.orderservice.common.interceptors;

import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
class GraphQlAuditLoggingInterceptor implements WebGraphQlInterceptor {

  @Override
  public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
    final var auditLogMessage = request.getDocument()
      + " accessed from "
      + request.getHeaders().getFirst("X-Forwarded-For");

    request.configureExecutionInput((executionInput, builder) ->
      builder.graphQLContext(Collections.singletonMap("auditLogMessage", auditLogMessage)).build());

    return chain.next(request);
  }
}
