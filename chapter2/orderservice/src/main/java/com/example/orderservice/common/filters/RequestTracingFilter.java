package com.example.orderservice.common.filters;

import com.example.orderservice.common.logger.LogLevel;
import com.example.orderservice.common.logger.Logger;
import com.example.orderservice.common.utils.ContentCachingRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.Collectors;

@Component
@Order(1)
public class RequestTracingFilter implements Filter {
  private final Logger logger;

  @Autowired
  public RequestTracingFilter(final Logger logger) {
    this.logger = logger;
  }

  @Override
  public void doFilter(
    ServletRequest servletRequest,
    ServletResponse servletResponse,
    FilterChain chain
  ) throws IOException, ServletException {
    final var httpRequest = (HttpServletRequest) servletRequest;
    final var contentCachingHttpRequest = new ContentCachingRequestWrapper(httpRequest);
    final var requestBody = contentCachingHttpRequest.getReader().lines().collect(Collectors.joining());
    logger.log(LogLevel.TRACE, httpRequest.getMethod() + " " + httpRequest.getRequestURI() + " " + requestBody);
    chain.doFilter(contentCachingHttpRequest, servletResponse);
  }
}
