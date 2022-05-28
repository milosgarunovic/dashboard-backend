package com.milosgarunovic.dashboard.api

import com.milosgarunovic.dashboard.domain.Task

class TaskUpdateRequest(
    val id: String,
    val name: String,
    val description: String? = null,
    val completed: Boolean,
)

fun TaskUpdateRequest.toTask() = Task(id, name, description, completed)