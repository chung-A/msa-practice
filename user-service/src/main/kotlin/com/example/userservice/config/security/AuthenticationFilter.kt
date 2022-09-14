package com.example.userservice.config.security

import com.example.userservice.model.user.RequestLogin
import com.example.userservice.model.user.ResponseUser
import com.example.userservice.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.core.env.Environment
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.lang.System.currentTimeMillis
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    private val userService: UserService,
    private val env: Environment
) : UsernamePasswordAuthenticationFilter() {

    private val objectMapper: ObjectMapper = ObjectMapper()
        .registerKotlinModule()

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {
        val token = objectMapper.readValue(request.inputStream, RequestLogin::class.java)
            .run { UsernamePasswordAuthenticationToken(this.email, this.password, emptyList()) }

        return authenticationManager.authenticate(token)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val user = userService.findByEmail((authResult.principal as User).username)
        val token = generateJwt(user)

        response.addHeader("token", token)
        response.addHeader("userId", user.userId)
    }

    private fun generateJwt(
        user: ResponseUser,
    ): String {
        val jwtExpirationTime = Date(
            currentTimeMillis()
                    + env.getProperty("token.expiration_time")!!.toLong()
        )
        return Jwts.builder()
            .setSubject(user.userId)
            .setExpiration(jwtExpirationTime)
            .claim("roles", "ROLE_ADMIN")
            .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
            .compact()
    }
}
