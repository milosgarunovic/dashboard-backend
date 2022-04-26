package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.domain.User
import com.milosgarunovic.dashboard.repository.UserRepositoryImpl
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/register")
class UserRegistrationController(
    val userRepositoryImpl: UserRepositoryImpl,
    val passwordEncoder: PasswordEncoder,
) {

    @PostMapping
    fun register(@RequestBody user: User) {
        val userCopy = user.copy(password = passwordEncoder.encode(user.password))
        userRepositoryImpl.add(userCopy)
    }

}