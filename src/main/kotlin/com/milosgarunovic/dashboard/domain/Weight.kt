package com.milosgarunovic.dashboard.domain

import com.milosgarunovic.dashboard.api.WeightResponse
import org.hibernate.annotations.GenericGenerator
import java.time.OffsetDateTime
import java.time.ZoneOffset
import javax.persistence.*

@Entity
@Table(name = "weights")
class Weight(
    @Id
    @GeneratedValue(generator = "tsid")
    @GenericGenerator(name = "tsid", strategy = "io.hypersistence.utils.hibernate.id.TsidGenerator")
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    val value: Double, // limit to some values and maximum of 1 decimal

    val dateCreated: OffsetDateTime = OffsetDateTime.now(),

    @Enumerated(EnumType.STRING)
    val unit: WeightUnit = WeightUnit.KG,
) {
    fun toWeightResponse(): WeightResponse {
        return WeightResponse(id, value, dateCreated.atZoneSameInstant(ZoneOffset.UTC), unit)
    }
}

enum class WeightUnit(val value: String) {
    KG("Kg"),
    LBS("lbs"),
}