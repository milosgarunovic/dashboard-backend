package com.milosgarunovic.dashboard.api

import javax.validation.constraints.Email
import javax.validation.constraints.Size

class RegisterRequest(
    @field:Email(message = "email field must be a well-formed email address")
    val email: String,

    @field:Size(min = 8, message = "password must be at least 8 characters long")
    val password: String,
)