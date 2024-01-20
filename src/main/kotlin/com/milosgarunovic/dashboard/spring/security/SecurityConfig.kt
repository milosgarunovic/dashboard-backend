package com.milosgarunovic.dashboard.spring.security

import com.milosgarunovic.dashboard.service.UserService
import com.milosgarunovic.dashboard.spring.UsernamePasswordAuthenticationProvider
import com.milosgarunovic.dashboard.spring.filter.JwtAuthorizationFilter
import com.milosgarunovic.dashboard.spring.filter.LoginAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@Configuration
class SecurityConfig(
    private val loginAuthenticationFilter: LoginAuthenticationFilter,
    private val jwtAuthorizationFilter: JwtAuthorizationFilter,
    private val usernamePasswordAuthenticationProvider: UsernamePasswordAuthenticationProvider,
) : WebSecurityConfigurerAdapter() {

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

    /**
     *  Used to provide public access to authenticationManager, it's protected in WebSecurityConfigurerAdapter
     */
    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    // TODO ??? what's this for anyway...
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider)
    }

    override fun configure(http: HttpSecurity) {
        http
            .addFilterBefore(loginAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .authorizeRequests()
            .mvcMatchers("/user/register").permitAll()
            .mvcMatchers("/task/**").hasRole("USER")
            .mvcMatchers("/weight/**").hasRole("USER")
            .mvcMatchers("/", "/**").permitAll()
            .and()
            .httpBasic().disable()
            .csrf().disable()
            .formLogin().disable()
            .logout().disable()
            .requestCache().disable()
            .cors().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration().apply {
            allowCredentials = true
            allowedOrigins = listOf("localhost", "127.0.0.1") // TODO see how to set this part up
            allowedMethods = listOf("GET", "POST", "DELETE")
        }
        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }

    /**
     * Removes the ROLE_ prefix in Spring Security
     * https://stackoverflow.com/a/43964633
     *
     * When defining a role, its name should start with the ROLE_ prefix. At the implementation level, this prefix
     * specifies the difference between a role and an authority.
     * - Spring Security in Action page 196
     */
    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults = GrantedAuthorityDefaults("")
}