package com.milosgarunovic.dashboard.spring

import com.milosgarunovic.dashboard.service.UserService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class UsernamePasswordAuthenticationProvider(val userService: UserService) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        val email = authentication.name
        val password = authentication.credentials as String

        val user = userService.getByEmail(email)
        if (user != null) {
            return UsernamePasswordAuthentication(email, password)
        }
        throw BadCredentialsException("Username or password is not correct.")
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthentication::class.java.isAssignableFrom(authentication)
    }

}

class UsernamePasswordAuthentication(
    username: String,
    password: String?,
) : UsernamePasswordAuthenticationToken(username, password, listOf(SimpleGrantedAuthority("USER")))