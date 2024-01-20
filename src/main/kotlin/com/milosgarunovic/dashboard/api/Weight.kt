package com.milosgarunovic.dashboard.api

import com.milosgarunovic.dashboard.domain.User
import com.milosgarunovic.dashboard.domain.Weight
import com.milosgarunovic.dashboard.domain.WeightUnit
import java.time.LocalDate
import java.util.*

open class WeightRequest(
    val value: Double, // TODO validation, positive, maximum of ?
    val date: LocalDate = LocalDate.now(),
    val unit: WeightUnit = WeightUnit.KG,
)

fun WeightRequest.toWeight(user: User) = Weight(UUID.randomUUID(), /*user,*/ value, date, unit)

class WeightUpdateRequest(
    val id: UUID,
    value: Double,
    date: LocalDate = LocalDate.now(),
    unit: WeightUnit = WeightUnit.KG,
) : WeightRequest(value, date, unit)

class WeightResponse(
    val id: UUID,
    val value: Double,
    val date: LocalDate,
    val unit: WeightUnit,
)
