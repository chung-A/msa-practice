package com.example.cataologservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthCheckController {

    @GetMapping()
    fun ping() = "welcome to catalog-service application!"

}
