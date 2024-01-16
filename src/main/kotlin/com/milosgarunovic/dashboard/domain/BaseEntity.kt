package com.milosgarunovic.dashboard.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.*
import javax.persistence.Id

abstract class BaseEntity(
    @Id open val id: UUID = UUID.randomUUID(),
    val dateAdded: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
    val dateDeleted: LocalDateTime? = null,
    val active: Boolean = true,
)