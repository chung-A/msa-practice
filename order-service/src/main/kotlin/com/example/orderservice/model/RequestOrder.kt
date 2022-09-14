package com.example.orderservice.model

data class RequestOrder(
    val productId:String,
    val qty:Long,
    val unitPrice:Long,
    val userId:String
) {
}
