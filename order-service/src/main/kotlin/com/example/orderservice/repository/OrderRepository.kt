package com.example.orderservice.repository

import com.example.orderservice.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {

    fun findByOrderId(orderId: String): Order?

    fun findByUserId(userId: String): List<Order>
}
