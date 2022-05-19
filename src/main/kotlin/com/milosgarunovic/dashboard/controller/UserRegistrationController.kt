package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.domain.User
import com.milosgarunovic.dashboard.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user/register")
class UserRegistrationController(
    val userService: UserService
) {

    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody user: User) {
        userService.add(user)
    }

}