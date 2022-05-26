package com.milosgarunovic.dashboard.spring.security

import com.milosgarunovic.dashboard.domain.User
import com.milosgarunovic.dashboard.service.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtSupport(
    private val userService: UserService,
) {

    // TODO move key to config?
    private final val accessTokenKey = "65c31aab-d804-46c3-bce5-b6086c5a0832"
    private val accessTokenSecretKey = Keys.hmacShaKeyFor(accessTokenKey.toByteArray())
    private val accessTokenParser = Jwts.parserBuilder().setSigningKey(accessTokenSecretKey).build()

    private final val refreshTokenKey = "65c31aab-d804-46c3-bce5-b6086c5a0833"
    private val refreshTokenSecretKey = Keys.hmacShaKeyFor(refreshTokenKey.toByteArray())
    private val refreshTokenParser = Jwts.parserBuilder().setSigningKey(refreshTokenSecretKey).build()

    fun generateAccessToken(username: String): String {
        val expiration: Date = Date.from(Instant.now().plus(3, ChronoUnit.MINUTES))
        // TODO add roles
        return generate(username, expiration, accessTokenSecretKey)
    }

    fun generateRefreshToken(username: String): String {
        val expiration: Date = Date.from(Instant.now().plus(14, ChronoUnit.DAYS))
        return generate(username, expiration, refreshTokenSecretKey)
    }

    private fun generate(username: String, expiration: Date, secretKey: SecretKey): String {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(expiration)
            .signWith(secretKey)
            .compact()
    }

    fun getUsernameFromAccessToken(token: String): String? {
        val username: String
        try {
            username = accessTokenParser.parseClaimsJws(token).body.subject
        } catch (e: Exception) {
            return null
        }
        return username
    }

    fun isAccessTokenValid(token: String, user: User?): Boolean {
        return user != null && isAccessTokenExpired(token)
    }

    fun generateAccessTokenIfRefreshTokenIsValid(refreshToken: String): String? {
        val username: String
        try {
            username = refreshTokenParser.parseClaimsJws(refreshToken).body.subject
        } catch (e: Exception) {
            return null
        }

        // if user exists continue
        if (userService.getByUsername(username) != null) {
            return null
        }

        if (isRefreshTokenExpired(refreshToken)) {
            return null
        }

        return generateAccessToken(username)
    }

    private fun isAccessTokenExpired(token: String): Boolean {
        return accessTokenParser.parseClaimsJws(token)
            .body
            .expiration
            .after(Date.from(Instant.now()))
    }

    private fun isRefreshTokenExpired(token: String): Boolean {
        return refreshTokenParser.parseClaimsJws(token)
            .body
            .expiration
            .after(Date.from(Instant.now()))
    }
}