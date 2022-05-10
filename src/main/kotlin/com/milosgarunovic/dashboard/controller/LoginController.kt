package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.repository.UserRepositoryImpl
import com.milosgarunovic.dashboard.spring.security.JwtSupport
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

    @PostMapping("/login", consumes = ["application/json"], produces = ["application/json"])
    fun login(@RequestBody login: Map<String, String>): ResponseEntity<Map<String, String>> {
        val username = login["username"]!!
        val user = userRepositoryImpl.getByUsernameOrEmail(username)

        user?.let {
            if (passwordEncoder.matches(login["password"], user.password)) {
                val token = jwtSupport.generateToken(username)
                val refreshToken = jwtSupport.generateRefreshToken(username)
                return ResponseEntity(mapOf("token" to token, "refreshToken" to refreshToken), HttpStatus.OK)
            }
        }

        return ResponseEntity(null, HttpStatus.UNAUTHORIZED)
    }

}