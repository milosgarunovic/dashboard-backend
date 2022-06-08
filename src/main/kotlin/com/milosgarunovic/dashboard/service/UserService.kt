package com.milosgarunovic.dashboard.service

import com.milosgarunovic.dashboard.domain.User
import com.milosgarunovic.dashboard.repository.UserRepositoryImpl
import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserService(
    val userRepositoryImpl: UserRepositoryImpl,
    @Lazy val passwordEncoder: PasswordEncoder,
) {

    fun add(username: String, password: String) {
        val userCopy = User(email = username, password = passwordEncoder.encode(password))
        userRepositoryImpl.add(userCopy)
    }

    fun getByEmail(username: String): User? {
        return userRepositoryImpl.getByEmail(username)
    }

}