package com.example.userservice.controller

import com.example.userservice.model.user.RequestUser
import com.example.userservice.model.user.ResponseUser
import com.example.userservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@Validated
@RequestMapping("/user")
class UserController(
    val userService: UserService
) {

    @GetMapping
    fun findAll(): List<ResponseUser> {
        return userService.findAll()
    }

    @PostMapping
    fun createUser(@RequestBody requestUser: RequestUser): ResponseEntity<ResponseUser> {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(
            userService.createUser(requestUser)
        )
    }

    @GetMapping("/{userId}")
    fun findByUserId(@PathVariable userId: String): ResponseUser {
        return userService.findByUserId(userId)
    }
}
