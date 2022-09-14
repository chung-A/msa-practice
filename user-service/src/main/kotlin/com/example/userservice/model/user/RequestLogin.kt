package com.example.userservice.model.user

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class RequestLogin(
    @NotEmpty
    @Email
    var email: String,
    @NotEmpty
    var password:String,
){
}
