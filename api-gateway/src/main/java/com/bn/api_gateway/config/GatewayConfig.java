package com.bn.api_gateway.config;

import com.bn.api_gateway.security.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("main-service", r -> r.path(
                                "/api/v1/user/**",
                                "/api/v1/test/**",
                                "/api/auth/**",
                                "/api/v1/jwt/**")
                        .uri("http://localhost:8081"))
                .build();
    }
}
