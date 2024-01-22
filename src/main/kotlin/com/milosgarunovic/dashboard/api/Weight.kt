package com.milosgarunovic.dashboard.api

import com.milosgarunovic.dashboard.domain.User
import com.milosgarunovic.dashboard.domain.Weight
import com.milosgarunovic.dashboard.domain.WeightUnit
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.util.*

open class WeightRequest(
    val value: Double, // TODO validation, positive, maximum of ?
    val date: OffsetDateTime = OffsetDateTime.now(), // TODO validation not in future
    val unit: WeightUnit = WeightUnit.KG,
)

fun WeightRequest.toWeight(user: User) = Weight(UUID.randomUUID(), user, value, date, unit)

class WeightUpdateRequest(
    val id: UUID,
    value: Double,
    date: OffsetDateTime = OffsetDateTime.now(),
    unit: WeightUnit = WeightUnit.KG,
) : WeightRequest(value, date, unit)

class WeightResponse(
    val id: UUID,
    val value: Double,
    val date: ZonedDateTime, // UTC always response, let users convert
    val unit: WeightUnit,
)
