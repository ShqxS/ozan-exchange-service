package com.ozan.exchange.configuration;

import com.ozan.exchange.interceptor.IncomingRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IncomingRequestInterceptor()).order(Ordered.HIGHEST_PRECEDENCE);
    }
}
