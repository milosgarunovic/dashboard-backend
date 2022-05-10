package com.milosgarunovic.dashboard.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Component
class JwtSupport {
    private val key = Keys.hmacShaKeyFor("65c31aab-d804-46c3-bce5-b6086c5a0832".toByteArray())
    private val parser = Jwts.parserBuilder().setSigningKey(key).build()

    fun generate(username: String): BearerToken {
        val builder = Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plus(3, ChronoUnit.MINUTES)))
            .signWith(key)
        return BearerToken(builder.compact())
    }

    fun getUsername(token: BearerToken): String {
        return parser.parseClaimsJws(token.value).body.subject
    }

    fun isValid(token: BearerToken, user: UserDetails?): Boolean {
        val claims = parser.parseClaimsJws(token.value).body
        val unexpired = claims.expiration.after(Date.from(Instant.now()))
        return unexpired && (claims.subject == user?.username)
    }
}

class BearerToken(val value: String) : AbstractAuthenticationToken(AuthorityUtils.NO_AUTHORITIES) {

    override fun getCredentials(): Any = value

    override fun getPrincipal(): Any = value
}