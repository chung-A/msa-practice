package com.example.gateway.config

import com.example.gateway.filter.JwtAuthorizationFilter
import com.example.gateway.filter.LoggingFilter
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod


@Configuration
class FilterConfig(
    private val loggingFilter: LoggingFilter,
    private val jwtAuthorizationFilter: JwtAuthorizationFilter
) {

    @Bean
    fun gatewayRoutes(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes()
            .route {
                // 로그인, 회원가입은 인증없이 route
                it.path("/user-service/login", "/user-service/user")
                    .and().method(HttpMethod.POST)
                    .filters { filterSpec ->
                        filterSpec.rewritePath("/user-service", "")
                            .removeRequestHeader("Cookie")
                    }.uri("lb://USER-SERVICE")
            }
            .route {
                it.path("/user-service/**")
                    .and().method(HttpMethod.GET)
                    .filters { filterSpec ->
                        filterSpec.rewritePath("/user-service", "")
                            .filter(jwtAuthorizationFilter)
                            .removeRequestHeader("Cookie")
                    }.uri("lb://USER-SERVICE")
            }.route {
                it.path("/catalog-service/**")
                    .filters { filter ->
                        filter.rewritePath("/catalog-service", "")
                    }
                    .uri("lb://CATALOG-SERVICE")
            }.route {
                it.path("/order-service/**")
                    .filters { filter ->
                        filter.rewritePath("/order-service", "")
                    }
                    .uri("lb://ORDER-SERVICE")
            }
            .build()
    }
}
