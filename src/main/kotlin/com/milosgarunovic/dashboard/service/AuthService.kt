package com.milosgarunovic.dashboard.service

import com.milosgarunovic.dashboard.spring.security.JwtSupport
import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AuthService(
    val userService: UserService,
    @Lazy val passwordEncoder: PasswordEncoder,
    val jwtSupport: JwtSupport,
) {

    fun login(email: String, password: String): Map<String, String>? {
        val user = userService.getByEmail(email)

        user?.let {
            if (passwordEncoder.matches(password, user.password)) {
                val token = jwtSupport.generateAccessToken(email)
                val refreshToken = jwtSupport.generateRefreshToken(email)
                return mapOf("accessToken" to token, "refreshToken" to refreshToken)
            }
        }
        return null
    }

    fun refreshToken(refreshToken: String): Map<String, String>? {
        if (refreshToken.startsWith("Bearer ")) {
            val tokenWithoutBearer = refreshToken.substring("Bearer ".length)
            val accessToken = jwtSupport.generateAccessTokenIfRefreshTokenIsValid(tokenWithoutBearer)
            if (accessToken != null) {
                return mapOf("accessToken" to accessToken)
            }
        }
        return null
    }

}