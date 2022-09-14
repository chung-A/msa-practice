package com.example.cataologservice.repository

import com.example.cataologservice.model.Catalog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CatalogRepository : JpaRepository<Catalog, Long> {

    fun findByProductId(productId: String): Catalog?
}
