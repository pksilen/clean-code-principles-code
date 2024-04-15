package com.example.orderservice.common.authorizer;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FakeAuthorizer implements Authorizer {
  @Override
  public void authorize(final Optional<String> maybeAuthHeader) {

  }

  @Override
  public void authorizeForSelf(final Optional<String> maybeUserId, final Optional<String> maybeAuthHeader) {

  }

  @Override
  public void authorizeIfUserHasOneOfRoles(final List<String> allowedRoles, final Optional<String> maybeAuthHeader) {
    System.out.println("Authorized for role(s) " + allowedRoles + ", with auth header: " + maybeAuthHeader.orElse(""));
  }

  @Override
  public String getUserId(final Optional<String> maybeAuthHeader) {
    return "";
  }
}
