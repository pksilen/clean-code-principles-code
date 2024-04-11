package com.example.orderservice.controllers.graphql;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class ExceptionResolver extends DataFetcherExceptionResolverAdapter {

  @Override
  protected GraphQLError resolveToSingleError(final Throwable throwable, final DataFetchingEnvironment environment) {
    throwable.printStackTrace();

    // This is just an example exception resolver for any throwable for demonstration purposes
    // You should map thrown errors/exceptions to proper GraphQL error messages here
    return GraphqlErrorBuilder.newError()
      .errorType(ErrorType.INTERNAL_ERROR)
      .message("Custom error handler")
      .path(environment.getExecutionStepInfo().getPath())
      .location(environment.getField().getSourceLocation())
      .build();
  }
}
