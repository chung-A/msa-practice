package com.example.userservice.model.order

import java.time.LocalDateTime

data class ResponseOrder(
    val productId: Long,
    val qty: Long,
    val unitPrice: Long,
    val totalPrice: Long,
    val createdAt: LocalDateTime,
    val orderId: String
)
