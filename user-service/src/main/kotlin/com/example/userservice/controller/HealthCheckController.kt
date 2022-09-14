package com.example.userservice.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthCheckController {

    val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping()
    fun ping(): String {
        log.info("health check!")
        return "welcome to user-service application!"
    }

}
