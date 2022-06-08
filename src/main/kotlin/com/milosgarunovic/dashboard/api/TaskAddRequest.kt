package com.milosgarunovic.dashboard.api

import com.milosgarunovic.dashboard.domain.Task
import javax.validation.constraints.NotBlank

class TaskAddRequest(
    @field:NotBlank
    val name: String,
    val description: String?,
)

fun TaskAddRequest.toTask() = Task(name = name, description = description)