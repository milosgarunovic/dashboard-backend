package com.milosgarunovic.dashboard.domain

import java.util.*

data class User(
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    val password: String,
)