package com.milosgarunovic.dashboard.spring.security

import com.milosgarunovic.dashboard.service.UserService
import com.milosgarunovic.dashboard.spring.filter.CustomAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@EnableWebSecurity
class SecurityConfig(
    private val jwtSupport: JwtSupport,
    private val userService: UserService,
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    /**
     * Instead of using the default user from spring, here we tell spring to use database as a source for users
     */
    @Bean
    fun userDetailsService(userService: UserService): UserDetailsService {
        return UserDetailsService { username: String ->
            val user = userService.getByEmail(username)
            if (user != null) {
                User(user.email, user.password, listOf(SimpleGrantedAuthority("USER")))
            } else {
                throw UsernameNotFoundException("User '$username' not found")
            }
        }
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .addFilterBefore(
                CustomAuthorizationFilter(jwtSupport, userService),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/user/register").permitAll()
            .antMatchers("/task/**").hasRole("USER")
            .antMatchers("/", "/**").permitAll()
            .and()
            .httpBasic().disable()
            .csrf().disable()
            .formLogin().disable()
            .cors().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .build()
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration().apply {
            allowCredentials = true
            addAllowedOrigin("*")
            addAllowedHeader("*")
            addAllowedMethod("*")
        }
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    /**
     * Removes the ROLE_ prefix in Spring Security
     * https://stackoverflow.com/a/43964633
     */
    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults = GrantedAuthorityDefaults("")
}