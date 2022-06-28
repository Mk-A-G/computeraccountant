package com.computeraccountant.computeraccountant.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(1)
public class RequestResponseLoggingFilter implements Filter {

  private final Logger LOG = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;
    LOG.info(
        "Logging Request  {} : {}", req.getMethod(),
        req.getRequestURI());
    res.setHeader("Access-Control-Allow-Origin", "*");
    chain.doFilter(request, response);
    LOG.info(
        "Logging Response :{}",
        res.getStatus());
  }

}
