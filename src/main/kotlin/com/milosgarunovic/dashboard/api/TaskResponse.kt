package com.milosgarunovic.dashboard.api

import java.util.UUID

data class TaskResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val completed: Boolean,
)