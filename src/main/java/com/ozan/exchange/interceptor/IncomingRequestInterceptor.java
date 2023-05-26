package com.ozan.exchange.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

import static com.ozan.exchange.utils.Constants.LOG_CORRELATION_ID;

@Slf4j
public class IncomingRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.clear();
        String correlationId = extractCorrelationIdFromHeader(request);
        MDC.put(LOG_CORRELATION_ID, correlationId);

        return true;
    }

    private static String extractCorrelationIdFromHeader(HttpServletRequest request) {
        String correlationId = request.getHeader(LOG_CORRELATION_ID);
        return correlationId != null
                ? correlationId
                : UUID.randomUUID().toString().replace("-", "");
    }

     
}
