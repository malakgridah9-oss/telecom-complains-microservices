package org.example.telecomcomplainsgateway.filter;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainsgateway.util.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private final JwtUtils jwtUtils;

    private static final List<String> PUBLIC_URLS = List.of(
            "/api/auth/login",
            "/api/auth/register"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (isPublicUrl(path)) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders()
                .getFirst("Authorization");

        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);

        if (!jwtUtils.validateToken(token)) {
            return unauthorized(exchange);
        }

        String email = jwtUtils.getEmailFromToken(token);
        String role = jwtUtils.getRoleFromToken(token);

        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-User-Email", email)
                .header("X-User-Role", role)
                .build();

        return chain.filter(
                exchange.mutate()
                        .request(modifiedRequest)
                        .build());
    }

    private boolean isPublicUrl(String path) {
        return PUBLIC_URLS.stream()
                .anyMatch(path::startsWith);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
