package com.milosgarunovic.dashboard.repository

import com.milosgarunovic.dashboard.domain.User
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component

@Component
class UserRepositoryImpl(val jdbcTemplate: NamedParameterJdbcTemplate) {

    fun add(user: User): User {
        //language=postgresql
        val query = "INSERT INTO users VALUES (:id::uuid, :username, :email, :password)"
        jdbcTemplate.update(
            query, mapOf(
                "id" to user.id,
                "username" to user.username,
                "email" to user.email,
                "password" to user.password,
            )
        )
        return user
    }

    fun getByUsernameOrEmail(usernameOrEmail: String): User? {
        //language=postgresql
        val query = "SELECT * FROM users WHERE username = :uOrE OR email = :uOrE"
        return jdbcTemplate.queryForObject(query, mapOf("uOrE" to usernameOrEmail)) { rs, _ ->
            User(
                rs.getString("id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password"),
            )
        }
    }
}