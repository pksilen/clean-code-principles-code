/* class RequestHeaderInterceptor implements WebGraphQlInterceptor {

  @Override
  public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
    String value = request.getHeaders().getFirst("myHeader");
    request.configureExecutionInput((executionInput, builder) ->
      builder.graphQLContext(Collections.singletonMap("myHeader", value)).build());
    return chain.next(request);
  }
} */
