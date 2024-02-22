package com.opay.apigateway.config;

import com.auth0.jwt.JWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Order(1)
@Component
public class AuthGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("UserIdFilter");

        if (exchange.getRequest().getHeaders().containsKey("Authorization")) {

            log.info("Authorization Header: {}", exchange.getRequest().getHeaders().get("Authorization"));

            String userId = JWT.decode(
                            exchange.getRequest().getHeaders().get("Authorization").get(0)
                                    .replace("Bearer ", "")
                    )
                    .getSubject().replace("\"", "");

            log.info("User-Id: {}", userId);
            exchange.mutate()
                    .request(
                            exchange.getRequest().mutate()
                                    .header("User-Id", userId)
                                    .build()
                    )
                    .build();
        }

        return chain.filter(exchange);
    }
}
