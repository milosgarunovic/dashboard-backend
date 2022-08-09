package com.milosgarunovic.dashboard.api

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class RegisterRequest(
    @field:Email(message = "email field must be a well-formed email address")
    @field:NotNull
    val email: String?,

    @field:Size(min = 8, message = "password must be at least 8 characters long")
    @field:NotNull
    val password: String?,
)