package com.milosgarunovic.dashboard.repository

import com.milosgarunovic.dashboard.domain.User
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component

@Component
class UserRepositoryImpl(val jdbcTemplate: NamedParameterJdbcTemplate) {

    fun add(user: User): User {
        //language=postgresql
        val query = "INSERT INTO users VALUES (:id::uuid, :email, :password)"
        jdbcTemplate.update(
            query, mapOf(
                "id" to user.id,
                "email" to user.email,
                "password" to user.password,
            )
        )
        return user
    }

    fun getByEmail(email: String): User? {
        //language=postgresql
        val query = "SELECT * FROM users WHERE email = :email"
        return try {
            jdbcTemplate.queryForObject(query, mapOf("email" to email)) { rs, _ ->
                User(
                    rs.getString("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                )
            }
        } catch (ex: EmptyResultDataAccessException) {
            null
        }
    }
}