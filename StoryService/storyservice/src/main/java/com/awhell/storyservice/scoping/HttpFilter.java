package com.awhell.storyservice.scoping;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import io.micrometer.tracing.Tracer;

@Component
public class HttpFilter implements Filter {

    private Tracer tracer;

    public HttpFilter(Tracer tracer) {
        this.tracer = tracer;
    }

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

        try {
            io.micrometer.tracing.Span currentSpan = tracer.currentSpan();
            if (currentSpan != null && currentSpan.context() != null) {

                String traceId = currentSpan != null ? currentSpan.context().traceId() : "no-trace-id";
                String spanId = currentSpan != null ? currentSpan.context().spanId() : "no-span-id";

                MDC.put("traceId", traceId);
                MDC.put("spanId", spanId);

                System.out.println("The TraceId In Filter" + traceId + "and Span id In Filter" + spanId);

            } else {
            MDC.put("traceId", "not-found-traceId");
                MDC.put("spanId", "Not-Found-SpanId");    

                System.out.println("The TraceId In Filter IS Not Found");
            }
        } catch (Exception e) {
            System.out.println("Error In Found In Filter" + e.getMessage());
        } finally {
            MDC.remove("traceId");
            MDC.remove("spanId");
        }

        chain.doFilter(request, response);
    }

}


// Note  - where it is only single class is use and it is before sevelet level  calling

//   ---------- > Filter1 ----------> Servelet1  ---------> Interceptor -------------> Controller


//   ---------- > Filter2 ----------> Servelet2  ---------> Interceptor2 -------------> Controller
//   ---------- > Filter3 ----------> Servelet3  ---------> Interceptor3 -------------> Controller