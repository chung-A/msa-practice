package com.example.userservice.model.user

import com.example.userservice.model.order.ResponseOrder

data class ResponseUser(
    val email: String,
    val name: String,
    val userId: String,
    val orders: List<ResponseOrder>
) {
    constructor(user: User) : this(
        email = user.email!!,
        name = user.name!!,
        userId = user.userId!!,
        orders = emptyList()
    )
}
