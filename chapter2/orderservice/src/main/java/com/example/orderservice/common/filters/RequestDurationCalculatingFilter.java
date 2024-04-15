package com.example.orderservice.common.filters;

import com.example.orderservice.common.metrics.Metrics;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(4)
public class RequestDurationCalculatingFilter implements Filter {
  private final Metrics metrics;

  @Autowired
  public RequestDurationCalculatingFilter(final Metrics metrics) {
    this.metrics = metrics;
  }

  @Override
  public void doFilter(
    ServletRequest servletRequest,
    ServletResponse servletResponse,
    FilterChain chain
  ) throws IOException, ServletException {
    final var httpRequest = (HttpServletRequest) servletRequest;
    final var timeBeforeRequestProcessing = System.nanoTime();
    chain.doFilter(httpRequest, servletResponse);
    final var timeWhenRequestProcessed = System.nanoTime();
    final var requestDurationInNs = timeWhenRequestProcessed - timeBeforeRequestProcessing;
    final var endpoint = httpRequest.getMethod() + " " + httpRequest.getRequestURI();
    metrics.addRequestDurationInNs(endpoint, requestDurationInNs);
  }
}
