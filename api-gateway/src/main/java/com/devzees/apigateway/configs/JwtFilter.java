package com.devzees.apigateway.configs;

import com.devzees.apigateway.helpers.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Component
public class JwtFilter extends AbstractGatewayFilterFactory<JwtFilter.Config> {
    @Autowired
    RoutingRequestValidator routingRequestValidator;

    @Autowired
    JwtUtils jwtUtils;

    public JwtFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (routingRequestValidator.isAuthenticated.test(exchange.getRequest())) {
                // Check for Authorization Header
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization Header is missing");
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).getFirst();

                if (authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7); // Remove 'Bearer ' prefix
                }

                try {
                    jwtUtils.validateToken(authHeader); // Validate Token using JjwtUtils
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired JWT token");
                }
            }
            return chain.filter(exchange); // Proceed if valid
        };
    }

    public static class Config {
        /*
        * ➡️ The Config class in AbstractGatewayFilterFactory is used to pass custom configuration properties to the filter when it is applied. It allows developers to make their filter more flexible and customizable.
        * ➡️ For example, you might want to enable/disable the filter for certain routes, log additional information, or set custom validation rules. The Config class allows you to do this by providing a place to hold these values. I didn't have any custom validation or setting, hence I keep config class empty.
        * */
    }

}
