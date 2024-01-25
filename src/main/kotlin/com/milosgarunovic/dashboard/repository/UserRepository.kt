package com.milosgarunovic.dashboard.repository

import com.milosgarunovic.dashboard.domain.User
import com.milosgarunovic.dashboard.domain.`typealias`.Email
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component

@Component
interface UserRepository : CrudRepository<User, Long> {

    fun getUserByEmail(email: Email): User?

}