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

    fun add(user: User) {
        val userCopy = user.copy(password = passwordEncoder.encode(user.password))
        userRepositoryImpl.add(userCopy)
    }

    fun getByUsername(username: String): User? {
        return userRepositoryImpl.getByUsernameOrEmail(username)
    }

}