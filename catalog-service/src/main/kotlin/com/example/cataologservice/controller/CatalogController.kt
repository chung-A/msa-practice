package com.example.cataologservice.controller

import com.example.cataologservice.model.ResponseCatalog
import com.example.cataologservice.service.CatalogService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/catalog")
class CatalogController(
    val catalogService: CatalogService
) {

    @GetMapping
    fun findAll(): List<ResponseCatalog> {
        return catalogService.findAll()
    }

    @GetMapping("/{productId}")
    fun findByProductId(@PathVariable productId: String): ResponseCatalog {
        return catalogService.findByProductId(productId)
    }

}
