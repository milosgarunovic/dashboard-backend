package com.milosgarunovic.dashboard.service

import com.milosgarunovic.dashboard.domain.User
import com.milosgarunovic.dashboard.exception.ResourceAlreadyExistsException
import com.milosgarunovic.dashboard.repository.UserRepositoryImpl
import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserService(
    val userRepository: UserRepositoryImpl,
    @Lazy val passwordEncoder: PasswordEncoder,
) {

    fun add(email: String, password: String) {
        if (userRepository.getUserByEmail(email) != null) {
            throw ResourceAlreadyExistsException("Email already in use.")
        }
        val userCopy = User(email = email, password = passwordEncoder.encode(password))
        userRepository.save(userCopy)
    }

    fun getByEmail(email: String): User? {
        return userRepository.getUserByEmail(email)
    }

}