package com.milosgarunovic.dashboard.service

import com.milosgarunovic.dashboard.domain.Email
import com.milosgarunovic.dashboard.domain.Password
import com.milosgarunovic.dashboard.domain.User
import com.milosgarunovic.dashboard.exception.ResourceAlreadyExistsException
import com.milosgarunovic.dashboard.repository.UserRepository
import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserService(
    val userRepository: UserRepository,
    @Lazy val passwordEncoder: PasswordEncoder,
) {

    fun add(email: Email, password: Password) {
        if (userRepository.getUserByEmail(email) != null) {
            throw ResourceAlreadyExistsException("Email already in use.")
        }
        val userCopy = User(email = email, password = passwordEncoder.encode(password))
        userRepository.save(userCopy)
    }

    fun getByEmail(email: Email): User? {
        return userRepository.getUserByEmail(email)
    }

    fun getById(id: UUID): User {
        return userRepository.findById(id).get()
    }

}