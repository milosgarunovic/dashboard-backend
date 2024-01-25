package com.milosgarunovic.dashboard.domain

import com.milosgarunovic.dashboard.util.IdGenerator
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id var id: Long = IdGenerator.longId(),
    var email: Email,
    var password: Password,
) /*: BaseEntity()*/