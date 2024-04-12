package com.example.orderservice.common.utils;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ContentCachingRequestWrapper extends HttpServletRequestWrapper {
  private byte[] cachedBytes;

  public ContentCachingRequestWrapper(HttpServletRequest request) throws IOException {
    super(request);
    cacheInputStream();
  }

  private void cacheInputStream() throws IOException {
    cachedBytes = super.getInputStream().readAllBytes();
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    final var byteArrayInputStream = new ByteArrayInputStream(cachedBytes);

    return new ServletInputStream() {
      @Override
      public int read() throws IOException {
        return byteArrayInputStream.read();
      }

      @Override
      public boolean isFinished() {
        return false;
      }

      @Override
      public boolean isReady() {
        return true;
      }

      @Override
      public void setReadListener(ReadListener readListener) {
        // Intentionally no operation
      }
    };
  }

  @Override
  public BufferedReader getReader() throws IOException {
    return new BufferedReader(new InputStreamReader(getInputStream()));
  }
}
