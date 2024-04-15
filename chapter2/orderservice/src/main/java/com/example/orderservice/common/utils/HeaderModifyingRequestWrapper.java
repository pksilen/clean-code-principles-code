package com.example.orderservice.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.*;

public class HeaderModifyingRequestWrapper extends HttpServletRequestWrapper {
  private final Map<String, String> modifiedHeaders = new HashMap<>();

  public HeaderModifyingRequestWrapper(final HttpServletRequest request) {
    super(request);
  }

  public void setHeader(final String name, final String value) {
    modifiedHeaders.put(name, value);
  }

  @Override
  public String getHeader(final String name) {
    final String headerValue = modifiedHeaders.get(name);
    return headerValue != null ? headerValue : super.getHeader(name);
  }

  @Override
  public Enumeration<String> getHeaders(final String name) {
    final String modifiedHeaderValue = modifiedHeaders.get(name);
    final String headerValue = modifiedHeaderValue != null ? modifiedHeaderValue : super.getHeader(name);
    return headerValue == null ? Collections.emptyEnumeration() : Collections.enumeration(List.of(headerValue));
  }

  @Override
  public Enumeration<String> getHeaderNames() {
    final var headerNames = super.getHeaderNames().asIterator();
    final var newHeaderNames = new HashSet<String>();

    while (headerNames.hasNext()) {
      final var headerName = headerNames.next();
      newHeaderNames.add(headerName);
    }

    newHeaderNames.add("X-Forwarded-For");
    return Collections.enumeration(newHeaderNames);
  }
}
