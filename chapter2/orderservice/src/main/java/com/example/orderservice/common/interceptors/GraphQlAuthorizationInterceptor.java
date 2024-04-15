package com.example.orderservice.common.interceptors;

import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
class GraphQlAuthorizationInterceptor implements WebGraphQlInterceptor {

  @Override
  public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
    final var authHeader = request.getHeaders().getFirst("Authorization");

    request.configureExecutionInput((executionInput, builder) ->
      builder.graphQLContext(Collections.singletonMap("authHeader", authHeader == null ? "" : authHeader)).build());

    return chain.next(request);
  }
}

