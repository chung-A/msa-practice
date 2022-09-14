package com.example.orderservice.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "orders")
class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var productId: String? = null

    @Column(nullable = false)
    var qty: Long? = null

    @Column(nullable = false)
    var unitPrice: Long? = null

    @Column(nullable = false)
    var totalPrice: Long? = null

    @Column(nullable = false)
    var userId: String? = null

    @Column(nullable = false, unique = true)
    var orderId: String? = null

    @Column(nullable = false)
    @CreatedDate
    var createdAt: LocalDateTime? = null

    companion object {
        fun of(requestOrder: RequestOrder): Order {
            return Order().apply {
                this.orderId = UUID.randomUUID().toString()
                this.productId = requestOrder.productId
                this.qty = requestOrder.qty
                this.userId = requestOrder.userId
                this.unitPrice = requestOrder.unitPrice

                this.totalPrice = this.qty!! * this.unitPrice!!
                this.createdAt = LocalDateTime.now()
            }
        }
    }
}
