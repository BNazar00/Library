package com.bn.api_gateway.security;

import com.bn.clients.user.dto.JwtParseResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
@Slf4j
public class AuthenticationFilter implements GatewayFilter {
    private static final String JWT_PARSE_URL = "http://localhost:8081/api/v1/jwt/parse";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AuthenticationFilter(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        try {
            String authHeader = request.getHeaders().getFirst("Authorization");
            if (authHeader != null) {
                String token = authHeader.substring(7);
                JwtParseResponseDto data = parseTokenIfValid(token);
                populateRequestWithHeaders(exchange, data);
            }
        } catch (Exception ex) {
            log.error("Error user authentication, {}", ex.getMessage());
            return onError(exchange, HttpStatus.FORBIDDEN);
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private JwtParseResponseDto parseTokenIfValid(String token) {
        return restTemplate.postForObject(JWT_PARSE_URL, token, JwtParseResponseDto.class);
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, JwtParseResponseDto data)
            throws JsonProcessingException {
        exchange.getRequest().mutate()
                .header("username", data.getUsername())
                .header("roles", objectMapper.writeValueAsString(data.getRoleData()))
                .build();
    }
}
