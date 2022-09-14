package com.example.userservice.config.security

import com.example.userservice.config.JwtAuthenticationFilter
import com.example.userservice.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
class WebSecurityConfig(
    private val userService: UserService,
    private val env: Environment
) {

    @Bean
    fun filterChain(
        httpSecurity: HttpSecurity,
        authenticationManager: AuthenticationManager
    ): SecurityFilterChain {
        val authFilter = getUsernamePasswordFilter(authenticationManager)
        return httpSecurity
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            .antMatchers("/**/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(authFilter)
            .addFilterAfter(JwtAuthenticationFilter(),AuthenticationFilter::class.java)
            .build()
    }


    private fun getUsernamePasswordFilter(authenticationManager: AuthenticationManager): AuthenticationFilter {
        return AuthenticationFilter(userService, env)
            .apply { setAuthenticationManager(authenticationManager) }
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
}
