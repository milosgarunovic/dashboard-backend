package com.milosgarunovic.dashboard.domain

import com.milosgarunovic.dashboard.api.TaskResponse
import java.util.*

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String? = null,
    val completed: Boolean = false,
//    val active: Boolean = true,
//    val tags: List<String>?,
//    val userId: String, // user that is the owner
//    val color: String? = null,
)

fun Task.toTaskResponse() = TaskResponse(id, name, description, completed)