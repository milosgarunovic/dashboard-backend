package com.milosgarunovic.dashboard.service

import com.milosgarunovic.dashboard.domain.User
import com.milosgarunovic.dashboard.exception.ResourceAlreadyExistsException
import com.milosgarunovic.dashboard.repository.UserRepositoryImpl
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ResponseStatus

@Component
class UserService(
    val userRepositoryImpl: UserRepositoryImpl,
    @Lazy val passwordEncoder: PasswordEncoder,
) {

    fun add(email: String, password: String) {
        if (userRepositoryImpl.getByEmail(email) != null) {
            throw ResourceAlreadyExistsException("Email already in use.")
        }
        val userCopy = User(email = email, password = passwordEncoder.encode(password))
        userRepositoryImpl.add(userCopy)
    }

    fun getByEmail(email: String): User? {
        return userRepositoryImpl.getByEmail(email)
    }

}