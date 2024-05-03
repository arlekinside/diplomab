package com.github.arlekinside.diploma.app.config.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var request = (HttpServletRequest) servletRequest;
        var response = (HttpServletResponse) servletResponse;
        var uuid = UUID.randomUUID();
        log.info("""
                >>> Processing request | Path: {} | Request UUID: {} |  Method: {} | Content type: {}""",
                request.getRequestURI(), uuid, request.getMethod(), request.getContentType());

        var start = Instant.now();
        filterChain.doFilter(request, response);
        var end = Instant.now();

        var duration = Duration.between(start, end).toMillis();

        log.info(""" 
                <<< Request processed | Path: {} | Request UUID: {} | Time: {} millis | Status: {} | Content type: {}""",
                request.getRequestURI(), uuid, duration, response.getStatus(), response.getContentType());
    }
}
