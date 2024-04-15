package com.example.orderservice.common.filters;


import com.example.orderservice.common.utils.HeaderModifyingRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(3)
public class RequestRemoteAddressHeaderAddingFilter implements Filter {
  private static final String X_FORWARDED_FOR = "X-Forwarded-For";

  @Override
  public void doFilter(
    ServletRequest servletRequest,
    ServletResponse servletResponse,
    FilterChain chain
  ) throws IOException, ServletException {
    final var httpRequest = (HttpServletRequest) servletRequest;
    final var headerModifyingHttpRequest = new HeaderModifyingRequestWrapper(httpRequest);

    final var clientAddress = httpRequest.getHeader(X_FORWARDED_FOR) != null
      ? httpRequest.getHeader(X_FORWARDED_FOR)
      : httpRequest.getRemoteAddr();

    headerModifyingHttpRequest.setHeader(X_FORWARDED_FOR, clientAddress);
    chain.doFilter(headerModifyingHttpRequest, servletResponse);
  }
}

