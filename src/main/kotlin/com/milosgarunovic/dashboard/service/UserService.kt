package com.milosgarunovic.dashboard.service

import com.milosgarunovic.dashboard.domain.User
import com.milosgarunovic.dashboard.domain.`typealias`.Email
import com.milosgarunovic.dashboard.domain.`typealias`.Password
import com.milosgarunovic.dashboard.repository.UserRepository
import com.milosgarunovic.dashboard.spring.exception.ResourceAlreadyExistsException
import com.milosgarunovic.dashboard.util.IdGenerator
import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserService(
    val userRepository: UserRepository,
    @Lazy val passwordEncoder: PasswordEncoder,
) {

    fun add(email: Email, password: Password) {
        if (userRepository.getUserByEmail(email) != null) {
            throw ResourceAlreadyExistsException("Email already in use.")
        }
        val userCopy = User(IdGenerator.longId(), email, passwordEncoder.encode(password))
        userRepository.save(userCopy)
    }

    fun getByEmail(email: Email): User? {
        return userRepository.getUserByEmail(email)
    }

    fun getById(id: Long): User {
        return userRepository.findById(id).get()
    }

}