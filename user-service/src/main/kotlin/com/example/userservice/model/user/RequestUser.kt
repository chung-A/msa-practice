package com.example.userservice.model.user

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class RequestUser(
    @NotBlank
    @Email
    val email: String,
    @NotBlank
    val name: String,
    @NotBlank
    val password: String
)
