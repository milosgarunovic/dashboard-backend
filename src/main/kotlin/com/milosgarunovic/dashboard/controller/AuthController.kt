package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.api.LoginRequest
import com.milosgarunovic.dashboard.service.AuthService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class AuthController(
    val authService: AuthService,
) {

    @PostMapping("/login", consumes = ["application/json"], produces = ["application/json"])
    fun login(@Valid @RequestBody login: LoginRequest): ResponseEntity<Map<String, String>> {
        val loginInfo = authService.login(login.email!!, login.password!!)
        if (loginInfo != null) {
            return ResponseEntity(loginInfo, HttpStatus.OK)
        }

        return ResponseEntity(null, HttpStatus.UNAUTHORIZED)
    }

    @GetMapping("/refreshToken", produces = ["application/json"])
    fun refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) refreshToken: String): ResponseEntity<Map<String, String>> {
        val newAccessToken = authService.refreshToken(refreshToken)
        if (newAccessToken != null) {
            return ResponseEntity(newAccessToken, HttpStatus.OK)
        }
        return ResponseEntity(null, HttpStatus.UNAUTHORIZED)
    }
}