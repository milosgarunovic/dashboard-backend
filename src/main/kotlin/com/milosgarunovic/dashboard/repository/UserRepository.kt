package com.milosgarunovic.dashboard.repository

import com.milosgarunovic.dashboard.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component

@Component
interface UserRepository : CrudRepository<User, String> {

    fun getUserByEmail(email: String): User?

}