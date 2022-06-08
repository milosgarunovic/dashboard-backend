package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.api.LoginRequest
import com.milosgarunovic.dashboard.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/user/register")
class UserRegistrationController(
    val userService: UserService
) {

    @PostMapping(consumes = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@Valid @RequestBody user: LoginRequest) {
        userService.add(user.email!!, user.password!!)
    }

}