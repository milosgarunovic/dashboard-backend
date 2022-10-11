package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.service.AuthService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    val authService: AuthService,
) {

    @GetMapping("/refreshToken", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) refreshToken: String): ResponseEntity<Map<String, String>> {
        val newAccessToken = authService.refreshToken(refreshToken)
        if (newAccessToken != null) {
            return ResponseEntity(newAccessToken, HttpStatus.OK)
        }
        return ResponseEntity(null, HttpStatus.UNAUTHORIZED)
    }
}