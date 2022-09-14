package com.example.orderservice.model

import java.io.Serializable
import java.time.LocalDateTime

data class ResponseOrder(
    val productId: String,
    val qty: Long,
    val unitPrice: Long,
    val totalPrice: Long,
    val orderId: String,
    val createdAt: LocalDateTime
) : Serializable {

    constructor(order: Order) : this(
        productId = order.productId!!,
        qty = order.qty!!,
        unitPrice = order.unitPrice!!,
        totalPrice = order.totalPrice!!,
        orderId = order.orderId!!,
        createdAt = order.createdAt!!
    )


}
