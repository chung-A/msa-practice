package com.example.cataologservice.service

import com.example.cataologservice.model.ResponseCatalog
import com.example.cataologservice.repository.CatalogRepository
import org.springframework.stereotype.Service

@Service
class CatalogService(
    private val catalogRepository: CatalogRepository
) {

    fun findAll(): List<ResponseCatalog> {
        return catalogRepository.findAll().map { ResponseCatalog(it) }
    }

    fun findByProductId(productId: String): ResponseCatalog {
        return catalogRepository.findByProductId(productId)
            ?.run { ResponseCatalog(this) }
            ?: throw RuntimeException("no matched bt product id > $productId")
    }

}
