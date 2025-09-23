package com.awhell.storyservice.helper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class HttpFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("Http Filter Triggered" + request.getRemoteAddr());
        String clientIp = request.getRemoteAddr();

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        String authHeader = httpReq.getHeader("Authorization");

        // if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        // httpRes.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // httpRes.getWriter().write("Missing or invalid Authorization header");
        // return;
        // }

        System.out
                .println("The Filter Request Is Execute :" + "Client IP: " + clientIp + ", Auth Header: " + authHeader);

        chain.doFilter(request, response);
    }

}
