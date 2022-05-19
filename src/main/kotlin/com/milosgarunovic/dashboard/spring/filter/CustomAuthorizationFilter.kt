package com.milosgarunovic.dashboard.spring.filter

import com.milosgarunovic.dashboard.service.UserService
import com.milosgarunovic.dashboard.spring.security.JwtSupport
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthorizationFilter(
    private val jwtSupport: JwtSupport,
    private val userService: UserService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val path = request.servletPath
        if (path == "/login" || path == "/refreshToken") {
            filterChain.doFilter(request, response)
            return
        }

        val header = request.getHeader(AUTHORIZATION)
        val bearer = "Bearer "
        if (header != null && header.startsWith(bearer)) {
            val token = header.substring(bearer.length)
            val username = jwtSupport.getUsername(token)
            val user = userService.getByUsername(username)
            if (jwtSupport.isValid(token, user)) {
                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(username, user!!.password, listOf(SimpleGrantedAuthority("USER")))
            } else {
                response.sendError(HttpStatus.FORBIDDEN.value())
                return
            }
        }

        filterChain.doFilter(request, response)
    }
}