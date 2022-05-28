package com.milosgarunovic.dashboard.api

data class TaskResponse(
    val id: String,
    val name: String,
    val description: String?,
    val completed: Boolean,
)