package com.example.gateway.filter

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Configuration
// GatewayFilter 로 커스텀 필터를 만들 수 있다(요건 사용하기 위해서는 따로 등록해줘야 함)
// GlobalFilter 는 따로 필터등록을 안해도 모든 요청에 대해 필터작동(빈으로 등록만 해주면 사용됨)
class LoggingFilter : GlobalFilter, Ordered {

    val log: Logger = LoggerFactory.getILoggerFactory().getLogger(this::class.java.name)

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        val response = exchange.response

        log.info("Gateway PRE filter \n ${getRequestInfo(request)}")

        //post filter 동작
        return chain.filter(exchange).then(Mono.fromRunnable {
            log.info("Gateway POST filter \n >code -> ${response.statusCode}")
        })
    }

    private fun getRequestInfo(request: ServerHttpRequest): String {
        return "request info \n > path: ${request.path}, > method: ${request.method}, >body ${request.body}, >params: ${request.queryParams}"
    }

    override fun getOrder(): Int {
        return Ordered.HIGHEST_PRECEDENCE
    }
}
