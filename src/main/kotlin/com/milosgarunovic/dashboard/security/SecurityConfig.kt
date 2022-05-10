package com.milosgarunovic.dashboard.security

import com.milosgarunovic.dashboard.repository.UserRepositoryImpl
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService(userRepo: UserRepositoryImpl): UserDetailsService {
        return UserDetailsService { username: String ->
            val user = userRepo.getByUsernameOrEmail(username)
            if (user != null) {
                User(user.username, user.password, listOf(SimpleGrantedAuthority("USER")))
            } else {
                throw UsernameNotFoundException("User '$username' not found")
            }
        }
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers("/user/register").permitAll()
            .antMatchers("/task/**").hasRole("USER")
            .antMatchers("/", "/**").permitAll()
            .and()
            .httpBasic().disable()
            .csrf().disable()
            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .build()
    }
}