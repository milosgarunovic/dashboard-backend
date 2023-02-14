package com.milosgarunovic.dashboard.domain

import com.milosgarunovic.dashboard.api.TaskResponse
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "tasks")
data class Task(
    @Id var id: UUID = UUID.randomUUID(),
    var name: String,
    var description: String? = null,
    var completed: Boolean = false,
//    val active: Boolean = true,
//    val tags: List<String>?,
//    val userId: String, // user that is the owner
//    val color: String? = null,
)

fun Task.toTaskResponse() = TaskResponse(id, name, description, completed)