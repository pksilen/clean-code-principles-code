package com.example.orderservice.common.filters;

import com.example.orderservice.common.metrics.Metrics;
import com.example.orderservice.common.utils.ContentCachingRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(2)
public class RequestCountingFilter implements Filter {
  private final Metrics metrics;

  @Autowired
  public RequestCountingFilter(final Metrics metrics) {
    this.metrics = metrics;
  }

  @Override
  public void doFilter(
    ServletRequest servletRequest,
    ServletResponse servletResponse,
    FilterChain chain
  ) throws IOException, ServletException {
    final var httpRequest = (HttpServletRequest) servletRequest;
    final var contentCachingHttpRequest = new ContentCachingRequestWrapper(httpRequest);

    // This is just a sample implementation for demonstration purposes, not production implementation
    metrics.incrementRequestCount(httpRequest.getMethod() + " " + httpRequest.getRequestURI());

    chain.doFilter(contentCachingHttpRequest, servletResponse);
  }
}
