package com.example.gateway.filter

import io.jsonwebtoken.Jwts
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtAuthorizationFilter(
    private val env: Environment
) : GatewayFilter {
    val log: Logger = LoggerFactory.getILoggerFactory().getLogger(this::class.java.name)

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        exchange.request.run {
            if (!this.headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                return isError(exchange, "has not token", HttpStatus.UNAUTHORIZED)
            }

            val jwt = this.headers[HttpHeaders.AUTHORIZATION]!![0]
                .replace("Bearer", "")

            if (!isValid(jwt)) {
                return isError(exchange, "is not valid token", HttpStatus.UNAUTHORIZED)
            }
        }

        return chain.filter(exchange)
    }

    private fun isError(exchange: ServerWebExchange, msg: String, status: HttpStatus): Mono<Void> {
        return exchange.response.run {
            this.statusCode = status
            log.error(msg)

            this.setComplete()
        }
    }

    private fun isValid(jwt: String): Boolean {
        try {
            val subject = Jwts.parser()
                .setSigningKey(env.getProperty("token.secret"))
                .parseClaimsJws(jwt)
                .body
                .subject

            // subject 값 검증
            if (subject.isNullOrBlank()) {
                return false
            }

        } catch (_: Exception) {
            // 올바른 jwt 형식인지 검증
            return false
        }

        return true
    }

}
