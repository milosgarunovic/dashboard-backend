package com.milosgarunovic.dashboard.spring.filter

import com.milosgarunovic.dashboard.spring.UsernamePasswordAuthentication
import com.milosgarunovic.dashboard.spring.security.JwtSupport
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthorizationFilter(
    private val jwtSupport: JwtSupport,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader(AUTHORIZATION)
        val bearer = "Bearer "
        if (header != null && header.startsWith(bearer)) {
            val token = header.substring(bearer.length)
            val username = jwtSupport.getUsernameFromAccessToken(token)
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthentication(username, null)
        } else {
            response.sendError(HttpStatus.FORBIDDEN.value())
            return
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        // skips this filter if path is /login or /refreshToken
        return request.servletPath.equals("/login") ||
                request.servletPath.equals("/refreshToken")
    }
}