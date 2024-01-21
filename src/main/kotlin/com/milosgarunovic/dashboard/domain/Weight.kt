package com.milosgarunovic.dashboard.domain

import com.milosgarunovic.dashboard.api.WeightResponse
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id

@Entity(name = "weights")
class Weight(
    @Id val id: UUID = UUID.randomUUID(),
//    val user: User,
    val value: Double, // limit to some values and maximum of 1 decimal
    val dateCreated: OffsetDateTime = OffsetDateTime.now(),
    @Enumerated(EnumType.STRING)
    val unit: WeightUnit = WeightUnit.KG,
)

enum class WeightUnit(val value: String) {
    KG("Kg"),
    LBS("lbs"),
}

fun Weight.toWeightResponse(): WeightResponse {
    return WeightResponse(id, value, dateCreated.atZoneSameInstant(ZoneOffset.UTC), unit)
}