package com.milosgarunovic.dashboard.repository

import com.milosgarunovic.dashboard.domain.Email
import com.milosgarunovic.dashboard.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
interface UserRepository : CrudRepository<User, UUID> {

    fun getUserByEmail(email: Email): User?

}