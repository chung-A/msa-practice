package com.example.cataologservice.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false, unique = true)
    val productId: String? = null

    @Column(nullable = false)
    var productName: String? = null

    @Column(nullable = false)
    var stock: Long? = null

    @Column(nullable = false)
    var unitPrice: Long? = null

    @Column(nullable = false)
    @CreatedDate
    var createdAt: LocalDateTime? = null
}
