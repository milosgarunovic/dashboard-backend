package com.milosgarunovic.dashboard.api

import com.milosgarunovic.dashboard.domain.Task

class TaskAddRequest(
    val name: String,
    val description: String?,
)

fun TaskAddRequest.toTask() = Task(name = name, description = description)