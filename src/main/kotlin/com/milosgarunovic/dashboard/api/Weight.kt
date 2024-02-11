package com.milosgarunovic.dashboard.api

import com.milosgarunovic.dashboard.api.validation.Result
import com.milosgarunovic.dashboard.domain.User
import com.milosgarunovic.dashboard.domain.Weight
import com.milosgarunovic.dashboard.domain.WeightUnit
import com.milosgarunovic.dashboard.util.IdGenerator
import java.time.OffsetDateTime
import java.time.ZonedDateTime

open class WeightRequest(
    val value: Double?,
    val date: OffsetDateTime?,
    val unit: WeightUnit = WeightUnit.KG,
) {
    open fun validate(): Result<WeightRequest> {
        val message = StringBuilder()
        commonValidation(message)

        return if (message.isEmpty()) {
            Result.Success(this)
        } else {
            Result.Failure(message.toString().trim())
        }
    }

    /**
     * appends to existing string builder validation messages
     */
    open fun commonValidation(message: StringBuilder) {
        if (value == null) {
            message.appendLine("value can't be null")
        } else if (value < 0) {
            message.appendLine("value can't be negative")
        } else if (value > 200) {
            message.appendLine("maximum value is 200")
        }

        if (date?.isAfter(OffsetDateTime.now()) == true) {
            message.appendLine("date can't be in future")
        }
    }

    fun toWeight(user: User): Weight {
        if (date == null) {
            return Weight(IdGenerator.longId(), user, value!!, OffsetDateTime.now(), unit)
        } else {
            return Weight(IdGenerator.longId(), user, value!!, date, unit)
        }
    }
}


class WeightUpdateRequest(
    val id: Long?,
    value: Double,
    date: OffsetDateTime,
    unit: WeightUnit,
) : WeightRequest(value, date, unit) {

    override fun validate(): Result<WeightUpdateRequest> {
        val message = StringBuilder()

        if (id == null) {
            message.appendLine("id can't be null")
        }

        commonValidation(message)

        return if (message.isEmpty()) {
            Result.Success(this)
        } else {
            Result.Failure(message.toString().trim())
        }
    }
}

class WeightResponse(
    val id: Long,
    val value: Double,
    val date: ZonedDateTime, // UTC always response, let users convert
    val unit: WeightUnit,
)
