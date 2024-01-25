package com.milosgarunovic.dashboard.repository

import com.milosgarunovic.dashboard.domain.Email
import com.milosgarunovic.dashboard.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component

@Component
interface UserRepository : CrudRepository<User, Long> {

    fun getUserByEmail(email: Email): User?

}