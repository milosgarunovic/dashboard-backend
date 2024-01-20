package com.milosgarunovic.dashboard.api

import com.milosgarunovic.dashboard.domain.Task
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class TaskAddRequest(
    @field:NotBlank
    val name: String,
    val description: String?,
)

fun TaskAddRequest.toTask() = Task(name = name, description = description)

data class TaskResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val completed: Boolean,
)

class TaskUpdateRequest(
    @field:Size(min = 36, max = 36)
    val id: UUID,
    @field:NotBlank
    val name: String,
    val description: String? = null,
    val completed: Boolean,
)

fun TaskUpdateRequest.toTask() = Task(id, name, description, completed)