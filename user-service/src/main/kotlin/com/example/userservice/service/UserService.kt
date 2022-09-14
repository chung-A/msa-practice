package com.example.userservice.service

import com.example.userservice.model.user.RequestUser
import com.example.userservice.model.user.ResponseUser
import com.example.userservice.model.user.User
import com.example.userservice.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder
) {
    fun findAll(): List<ResponseUser> {
        return userRepository.findAll().map {
            ResponseUser(it)
        }
    }

    fun createUser(requestUser: RequestUser): ResponseUser {
        return User.of(requestUser, passwordEncoder.encode(requestUser.password))
            .apply { userRepository.save(this) }
            .run { ResponseUser(this) }
    }

    fun findByUserId(userId: String): ResponseUser {
        return userRepository.findByUserId(userId)
            ?.run { ResponseUser(this) }
            ?: throw UsernameNotFoundException("userId($userId) not found!")
    }

    fun findByEmail(email: String): ResponseUser {
        return userRepository.findByEmail(email)
            ?.run { ResponseUser(this) }
            ?: throw UsernameNotFoundException("userId($email) not found!")
    }

}
