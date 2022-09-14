package com.example.userservice.service

import com.example.userservice.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails? {
        return userRepository.findByEmail(username)
            ?.run {
                User(
                    this.email, this.encryptedPwd,
                    true, true, true, true,
                    listOf(GrantedAuthority { "ROLE_ADMIN" }).toMutableList()
                )
            }
            ?: throw UsernameNotFoundException("user name not exists! > $username")
    }
}
