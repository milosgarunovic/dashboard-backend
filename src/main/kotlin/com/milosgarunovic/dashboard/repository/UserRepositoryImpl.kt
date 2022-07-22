package com.milosgarunovic.dashboard.repository

import com.milosgarunovic.dashboard.domain.User
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.CrudRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component

@Component
interface UserRepositoryImpl : CrudRepository<User, String> {

    fun getUserByEmail(email: String): User?

}