package com.url.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

public class DumpFilter extends OncePerRequestFilter {
    private static final String NEWLINE = System.lineSeparator();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper bufferedRequest = new ContentCachingRequestWrapper(request, 2 * 1024 * 1024);
        ContentCachingResponseWrapper bufferedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(bufferedRequest, bufferedResponse);
        } finally {
            if (logger.isDebugEnabled()) {
                logger.debug(generateRequest(bufferedRequest, false));
                logger.debug(generateResponse(bufferedResponse, false));
            }
            bufferedResponse.copyBodyToResponse();
        }
    }

    private String generateRequest(ContentCachingRequestWrapper request, boolean isFileUpload) throws IOException {
        String protocol = "http".equals(request.getProtocol()) ? "HTTP/1.1" : request.getProtocol();
        StringBuilder sb = new StringBuilder()
                .append(NEWLINE)
                .append("> ").append(request.getMethod()).append(" ").append(request.getRequestURI()).append(generateQueryString(request)).append(" ").append(" ").append(protocol).append(NEWLINE)
                .append(generateRequestHeaders(request))
                .append("> ").append(NEWLINE);

        String requestBody = new String(request.getContentAsByteArray(), request.getCharacterEncoding());
        sb
            .append("> ")
            .append(isFileUpload ? "" : requestBody.replace("\n", "\n> "))
            .append(NEWLINE);
        return sb.toString();
    }

    private String generateResponse(ContentCachingResponseWrapper response, Boolean isFileUpload) throws IOException {
        StringBuilder sb = new StringBuilder()
                .append(NEWLINE)
                .append("< ").append("HTTP/1.1 ").append(response.getStatus()).append(" ").append(HttpStatus.valueOf(response.getStatus()).getReasonPhrase()).append(NEWLINE)
                .append(generateResponseHeaders(response))
                .append("< ").append(NEWLINE);

        String responseBody = new String(response.getContentAsByteArray(), response.getCharacterEncoding());
        sb
            .append("< ")
            .append(responseBody.replace("\n", "\n< "))
            .append(NEWLINE);

        return sb.toString();
    }

    private String generateResponseHeaders(HttpServletResponse response) {
        Collection<String> responseHeadersNames = response.getHeaderNames();
        final StringBuilder responseHeaders = new StringBuilder();

        for (String header : responseHeadersNames) {
            responseHeaders
                    .append("< ")
                    .append(header)
                    .append(": ")
                    .append(response.getHeader(header))
                    .append(NEWLINE);
        }
        return responseHeaders.toString();
    }

    private StringBuilder generateRequestHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        final StringBuilder headers = new StringBuilder();

        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            String value = request.getHeader(header);
            headers
                    .append("> ")
                    .append(header)
                    .append(": ")
                    .append(value)
                    .append(NEWLINE);
        }
        return headers;
    }

    private StringBuilder generateQueryString(HttpServletRequest request) throws UnsupportedEncodingException {
        final StringBuilder qs = new StringBuilder();

        for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
            for (String value : e.getValue()) {
                qs.append("&").append(e.getKey()).append("=").append(URLEncoder.encode(value, "utf-8"));
            }
        }
        if (qs.length() > 0) {
            qs.replace(0, 1, "?");
        }
        return qs;
    }
}
