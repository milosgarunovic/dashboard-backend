package com.milosgarunovic.dashboard.spring.security

import com.milosgarunovic.dashboard.domain.User
import com.milosgarunovic.dashboard.repository.UserRepositoryImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Component
class JwtSupport(
    private val userRepo: UserRepositoryImpl
) {
    private val key = Keys.hmacShaKeyFor("65c31aab-d804-46c3-bce5-b6086c5a0832".toByteArray())
    private val parser = Jwts.parserBuilder().setSigningKey(key).build()

    fun generateToken(username: String): String {
        val expiration: Date = Date.from(Instant.now().plus(3, ChronoUnit.MINUTES))
        // TODO add roles
        return generate(username, expiration)
    }

    fun generateRefreshToken(username: String): String {
        val expiration: Date = Date.from(Instant.now().plus(14, ChronoUnit.DAYS))
        return generate(username, expiration)
    }

    private fun generate(username: String, expiration: Date): String {
        val builder = Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(expiration)
            .signWith(key)
        return builder.compact()
    }

    fun getUsername(token: String): String {
        return parser.parseClaimsJws(token).body.subject
    }

    fun isValid(token: String, user: User?): Boolean {
        return user != null && isExpired(token)
    }

    fun isValid(token: String): Boolean {
        val username = getUsername(token)

        // check if user exists
        // TODO optimize so it doesn't call db on every req
        userRepo.getByUsernameOrEmail(username) ?: return false

        // if user exists, check if the token hasn't expired
        return isExpired(token)
    }

    private fun isExpired(token: String): Boolean {
        return parser.parseClaimsJws(token)
            .body
            .expiration
            .after(Date.from(Instant.now()))
    }
}