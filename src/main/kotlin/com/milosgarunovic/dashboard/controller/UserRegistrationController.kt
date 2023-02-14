package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.api.RegisterRequest
import com.milosgarunovic.dashboard.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/user/register")
class UserRegistrationController(
    val userService: UserService
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@Valid @RequestBody user: RegisterRequest) {
        userService.add(user.email, user.password)
    }
}