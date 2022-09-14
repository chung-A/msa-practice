package com.example.orderservice.service

import com.example.orderservice.model.Order
import com.example.orderservice.model.RequestOrder
import com.example.orderservice.model.ResponseOrder
import com.example.orderservice.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {

    fun createOrder(requestOrder: RequestOrder) {
        val order = Order.of(requestOrder)
        orderRepository.save(order)
    }

    fun findOrderById(orderId: String): ResponseOrder {
        return orderRepository.findByOrderId(orderId)
            ?.run { ResponseOrder(this) }
            ?: throw RuntimeException("not found matched order id! >$orderId")
    }

    fun findByUserId(userId: String): List<ResponseOrder> {
        return orderRepository.findByUserId(userId)
            .map { ResponseOrder(it) }
    }

}
