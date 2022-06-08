package com.milosgarunovic.dashboard.spring.security

import com.milosgarunovic.dashboard.domain.User
import com.milosgarunovic.dashboard.service.UserService
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.annotation.PostConstruct
import javax.crypto.SecretKey

@Component
class JwtSupport(
    private val userService: UserService,
    @Value("\${dashboard.access-token-key}") private val accessTokenKey: String,
    @Value("\${dashboard.refresh-token-key}") private val refreshTokenKey: String,
) {
    lateinit var accessTokenSecretKey: SecretKey
    lateinit var accessTokenParser: JwtParser
    lateinit var refreshTokenSecretKey: SecretKey
    lateinit var refreshTokenParser: JwtParser

    @PostConstruct
    fun init() {
        accessTokenSecretKey = Keys.hmacShaKeyFor(accessTokenKey.toByteArray())
        accessTokenParser = Jwts.parserBuilder().setSigningKey(accessTokenSecretKey).build()

        refreshTokenSecretKey = Keys.hmacShaKeyFor(refreshTokenKey.toByteArray())
        refreshTokenParser = Jwts.parserBuilder().setSigningKey(refreshTokenSecretKey).build()
    }

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
        if (userService.getByEmail(username) != null) {
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