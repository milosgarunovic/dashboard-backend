package com.milosgarunovic.dashboard.domain

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.hibernate.annotations.GenericGenerator
import javax.persistence.GeneratedValue
import javax.persistence.Id

abstract class BaseEntity(
    @Id
    @GeneratedValue(generator = "tsid")
    @GenericGenerator(name = "tsid", strategy = "io.hypersistence.utils.hibernate.id.TsidGenerator")
    open val id: Long,
    val dateAdded: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
    val dateDeleted: LocalDateTime? = null,
    val active: Boolean = true,
)