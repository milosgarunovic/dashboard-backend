package com.milosgarunovic.dashboard.domain

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "users")
data class User(
    @Id override var id: UUID = UUID.randomUUID(),
    var email: String,
    var password: String,
) : BaseEntity()