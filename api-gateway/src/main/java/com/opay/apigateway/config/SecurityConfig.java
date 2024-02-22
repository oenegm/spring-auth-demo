package com.opay.apigateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig implements WebFluxConfigurer {

    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {

        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .authorizeExchange(
                        auth -> auth

                                .pathMatchers(HttpMethod.OPTIONS).permitAll()

                                .pathMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
                                .pathMatchers(HttpMethod.POST, "/api/v1/auth/refresh-token").permitAll()
                                .pathMatchers(HttpMethod.POST, "/api/v1/users").permitAll()

                                .anyExchange().authenticated()
                );

        return http.build();
    }
}
