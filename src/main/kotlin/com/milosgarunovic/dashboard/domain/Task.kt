package com.milosgarunovic.dashboard.domain

import com.milosgarunovic.dashboard.api.TaskResponse
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "tasks")
data class Task(
    @Id val id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String? = null,
    val completed: Boolean = false,
//    val active: Boolean = true,
//    val tags: List<String>?,
//    val userId: String, // user that is the owner
//    val color: String? = null,
)

fun Task.toTaskResponse() = TaskResponse(id, name, description, completed)