package com.milosgarunovic.dashboard.domain

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "users")
data class User(
    @Id val id: UUID = UUID.randomUUID(),
    val email: String,
    val password: String,
)