package com.milosgarunovic.dashboard.service

import com.milosgarunovic.dashboard.repository.UserRepositoryImpl
import com.milosgarunovic.dashboard.spring.security.JwtSupport
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AuthService(
    val userRepositoryImpl: UserRepositoryImpl,
    val passwordEncoder: PasswordEncoder,
    val jwtSupport: JwtSupport
) {

    fun login(username: String, password: String): Map<String, String>? {
        val user = userRepositoryImpl.getByUsernameOrEmail(username)

        user?.let {
            if (passwordEncoder.matches(password, user.password)) {
                val token = jwtSupport.generateAccessToken(username)
                val refreshToken = jwtSupport.generateRefreshToken(username)
                return mapOf("accessToken" to token, "refreshToken" to refreshToken)
            }
        }
        return null
    }

    fun refreshToken(refreshToken: String): Map<String, String>? {
        if (refreshToken.startsWith("Bearer ")) {
            val tokenWithoutBearer = refreshToken.substring("Bearer ".length)
            val username = jwtSupport.getUsername(tokenWithoutBearer)
            if (jwtSupport.isValid(tokenWithoutBearer)) {
                val accessToken = jwtSupport.generateAccessToken(username)
                return mapOf("accessToken" to accessToken)
            }
        }
        return null
    }

}