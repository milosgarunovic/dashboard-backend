package com.milosgarunovic.dashboard.repository

import com.milosgarunovic.dashboard.domain.Weight
import com.milosgarunovic.dashboard.domain.WeightUnit
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

interface WeightRepository : CrudRepository<Weight, Long> {

    // TODO add limit to for example 100, that's around 3 months worth of data if users measure weight once per day. Or
    //  maybe select just last 3 months, and return any number of records. I'll probably have filter for how many months
    //  to select.
    fun findAllByUserIdOrderByDateCreatedDesc(userId: Long): List<Weight>

    @Transactional
    fun deleteByIdAndUserId(id: Long, userId: Long)

    @Transactional
    @Modifying
    @Query("UPDATE Weight SET value = :value, unit = :unit, dateCreated = :dateCreated WHERE user.id = :userId AND id = :id ")
    fun update(
        @Param("value") value: Double,
        @Param("unit") unit: WeightUnit,
        @Param("dateCreated") dateCreated: OffsetDateTime,
        @Param("userId") userId: Long,
        @Param("id") id: Long
    )
}