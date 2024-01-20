package com.milosgarunovic.dashboard.domain

import java.time.LocalDate
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "weights")
class Weight(
    @Id val id: UUID = UUID.randomUUID(),
//    val user: User,
    val value: Double, // limit to some values and maximum of 1 decimal
    val dateCreated: LocalDate = LocalDate.now(),
    val unit: WeightUnit = WeightUnit.KG,
)

enum class WeightUnit(val value: String) {
    KG("Kg"),
    LBS("lbs"),
}