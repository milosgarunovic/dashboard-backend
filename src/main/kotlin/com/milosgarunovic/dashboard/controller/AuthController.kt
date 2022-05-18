package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.repository.UserRepositoryImpl
import com.milosgarunovic.dashboard.spring.security.JwtSupport
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
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
                val token = jwtSupport.generateAccessToken(username)
                val refreshToken = jwtSupport.generateRefreshToken(username)
                return ResponseEntity(mapOf("accessToken" to token, "refreshToken" to refreshToken), HttpStatus.OK)
            }
        }

        return ResponseEntity(null, HttpStatus.UNAUTHORIZED)
    }

    @GetMapping("/refreshToken", produces = ["application/json"])
    fun refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) refreshToken: String): ResponseEntity<Map<String, String>> {
        if (refreshToken.startsWith("Bearer ")) {
            val tokenWithoutBearer = refreshToken.substring("Bearer ".length)
            val username = jwtSupport.getUsername(tokenWithoutBearer)
            if (jwtSupport.isValid(tokenWithoutBearer)) {
                val accessToken = jwtSupport.generateAccessToken(username)
                return ResponseEntity(mapOf("accessToken" to accessToken), HttpStatus.OK)
            }
        }
        return ResponseEntity(null, HttpStatus.UNAUTHORIZED)
    }
}