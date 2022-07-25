package com.milosgarunovic.dashboard.spring.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.milosgarunovic.dashboard.api.LoginRequest
import com.milosgarunovic.dashboard.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class InitialAuthenticationFilter(
    private val objectMapper: ObjectMapper,
    private val authService: AuthService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val bodyAsJsonString = request.reader.lines().collect(Collectors.joining(System.lineSeparator()))
        val loginRequest = objectMapper.readValue(bodyAsJsonString, LoginRequest::class.java)
        val jwt = authService.login(loginRequest.email!!, loginRequest.password!!)

        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        if (jwt != null) {
            response.status = HttpStatus.OK.value()
            response.writer.write(objectMapper.writeValueAsString(jwt))
            response.writer.flush()
            return
        }

        response.status = HttpStatus.UNAUTHORIZED.value()
        response.writer.write(objectMapper.writeValueAsString(mapOf("message" to "Username or password is not correct.")))
        response.writer.flush()

        // this doesn't call filterChain.doFilter() because this returns a response, acts like a /login path
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        // filter works if the path is /login
        // TODO what about refresh token?
        return !request.servletPath.equals("/login")
//                || !request.servletPath.equals("/refreshToken")
    }
}