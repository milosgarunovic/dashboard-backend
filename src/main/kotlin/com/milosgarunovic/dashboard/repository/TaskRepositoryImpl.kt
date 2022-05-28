package com.milosgarunovic.dashboard.repository

import com.milosgarunovic.dashboard.domain.Task
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component

@Component
class TaskRepositoryImpl(
    val jdbcTemplate: JdbcTemplate,
    val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) {

    fun add(task: Task): Task {
        //language=postgresql
        val query = "INSERT INTO tasks VALUES (:id::uuid, :name, :description, :completed)"
        namedParameterJdbcTemplate.update(
            query,
            mapOf(
                "id" to task.id,
                "name" to task.name,
                "description" to task.description,
                "completed" to task.completed
            )
        )
        return task
    }

    fun update(task: Task): Task {
        //language=postgresql
        val query =
            "UPDATE tasks SET name = :name, description = :description, completed = :completed WHERE id = :id::uuid"
        namedParameterJdbcTemplate.update(
            query, mapOf(
                "id" to task.id,
                "name" to task.name,
                "description" to task.description,
                "completed" to task.completed
            )
        )
        return task
    }

    fun getAll(): List<Task> {
        //language=postgresql
        val query = "SELECT * FROM tasks"
        return jdbcTemplate.query(query) { rs, _ ->
            Task(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBoolean("completed"),
            )
        }
    }

    fun getById(id: String): Task? {
        //language=postgresql
        val query = "SELECT * FROM tasks WHERE id = :id::uuid"
        return namedParameterJdbcTemplate.query(query, mapOf("id" to id)) { rs, _ ->
            Task(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBoolean("completed"),
            )
        }.firstOrNull()
    }

    fun delete(id: String) {
        //language=postgresql
        val query = "DELETE FROM tasks WHERE id = :id::uuid"
        namedParameterJdbcTemplate.update(query, mapOf("id" to id))
    }

    fun complete(id: String) {
        //language=postgresql
        val query = "UPDATE tasks SET completed = true WHERE id = :id::uuid"
        namedParameterJdbcTemplate.update(query, mapOf("id" to id))
    }
}