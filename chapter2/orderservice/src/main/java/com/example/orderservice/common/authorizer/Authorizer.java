package com.example.orderservice.common.authorizer;

import java.util.List;
import java.util.Optional;

public interface Authorizer {
  static class UnauthenticatedError extends RuntimeException {
  }

  static class UnauthorizedError extends RuntimeException {
  }

  static class IamError extends RuntimeException {
  }

  void authorize(final Optional<String> maybeAuthHeader);

  void authorizeForSelf(final Optional<String> maybeUserId, final Optional<String> maybeAuthHeader);

  void authorizeIfUserHasOneOfRoles(
    final List<String> allowedRoles,
    final Optional<String> maybeAuthHeader
  );

  String getUserId(final Optional<String> maybeAuthHeader);
}
