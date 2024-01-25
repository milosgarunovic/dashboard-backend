package com.milosgarunovic.dashboard.api

import com.milosgarunovic.dashboard.domain.`typealias`.Email
import javax.validation.constraints.Size

class RegisterRequest(
    @field:javax.validation.constraints.Email(message = "email field must be a well-formed email address")
    val email: Email,

    @field:Size(min = 8, message = "password must be at least 8 characters long")
    val password: String,
)