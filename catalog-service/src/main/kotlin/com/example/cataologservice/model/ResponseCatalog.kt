package com.example.cataologservice.model

import java.io.Serializable
import java.time.LocalDateTime

data class ResponseCatalog(
    val productId: String,
    val productName: String,
    val unitPrice: Long,
    val stock: Long,
    val createdAt: LocalDateTime
) : Serializable {

    constructor(catalog: Catalog) : this(
        productId = catalog.productId!!,
        productName = catalog.productName!!,
        unitPrice = catalog.unitPrice!!,
        stock = catalog.stock!!,
        createdAt = catalog.createdAt!!
    )


}
