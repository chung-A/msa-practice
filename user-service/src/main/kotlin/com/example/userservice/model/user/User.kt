package com.example.userservice.model.user

import java.util.*
import javax.persistence.*

@Entity
class User(
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var name: String? = null

    @Column(nullable = false, unique = true)
    var email: String? = null

    @Column(nullable = false, unique = true)
    var userId: String? = null

    @Column(nullable = false, unique = true)
    var encryptedPwd: String? = null

    companion object {
        fun of(requestUser: RequestUser, encodedPwd:String): User {
            return User().apply {
                this.userId = UUID.randomUUID().toString()
                this.name = requestUser.name
                this.email = requestUser.email
                this.encryptedPwd = encodedPwd
            }
        }
    }
}
