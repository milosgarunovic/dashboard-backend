package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.repository.UserRepositoryImpl
import com.milosgarunovic.dashboard.security.JwtSupport
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
    val userRepositoryImpl: UserRepositoryImpl,
    val passwordEncoder: PasswordEncoder,
    val jwtSupport: JwtSupport
) {

    @PostMapping("/login")
    fun login(@RequestBody login: Map<String, String>): ResponseEntity<Jwt> {
        val user = userRepositoryImpl.getByUsernameOrEmail(login["username"]!!)

        user?.let {
            if (passwordEncoder.matches(login["password"], user.password)) {
                val token = jwtSupport.generate(login["username"]!!).value
                return ResponseEntity(Jwt(token), HttpStatus.OK)
            }
        }

        return ResponseEntity(null, HttpStatus.UNAUTHORIZED)
    }

}

data class Jwt(val token: String)