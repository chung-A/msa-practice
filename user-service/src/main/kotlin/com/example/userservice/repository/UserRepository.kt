package com.example.userservice.repository

import com.example.userservice.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByUserId(userId: String): User?

    fun findByEmail(email: String): User?
}
