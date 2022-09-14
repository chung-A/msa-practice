package com.example.orderservice.controller

import com.example.orderservice.model.RequestOrder
import com.example.orderservice.model.ResponseOrder
import com.example.orderservice.service.OrderService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/order")
class OrderController(
    val orderService: OrderService
) {

    @GetMapping("/{userId}")
    fun findByUserId(@PathVariable userId: String): List<ResponseOrder> {
        return orderService.findByUserId(userId)
    }

    @PostMapping
    fun createOrders(@RequestBody requestOrder: RequestOrder) {
        orderService.createOrder(requestOrder)
    }

}
