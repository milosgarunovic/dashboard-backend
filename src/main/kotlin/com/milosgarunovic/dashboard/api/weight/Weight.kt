package com.milosgarunovic.dashboard.api.weight

import java.time.LocalDate
import java.util.*

open class WeightRequest(
    val value: Double,
    val date: LocalDate = LocalDate.now(),
    val unit: WeightUnit = WeightUnit.KG,
)

class WeightUpdateRequest(
    val id: UUID,
    value: Double,
    date: LocalDate = LocalDate.now(),
    unit: WeightUnit = WeightUnit.KG,
) : WeightRequest(value, date, unit)

enum class WeightUnit(val value: String) {
    KG("Kg"),
    LBS("lbs"),
}
