package com.milosgarunovic.dashboard.repository

import com.milosgarunovic.dashboard.domain.Weight
import com.milosgarunovic.dashboard.domain.WeightUnit
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.util.*

interface WeightRepository : CrudRepository<Weight, UUID> {

    fun findAllByUserIdOrderByDateCreatedDesc(userId: UUID): List<Weight>

    @Transactional
    fun deleteByIdAndUserId(id: UUID, userId: UUID)

    @Transactional
    @Modifying
    @Query("UPDATE Weight SET value = :value, unit = :unit, dateCreated = :dateCreated WHERE user.id = :userId AND id = :id ")
    fun update(
        @Param("value") value: Double,
        @Param("unit") unit: WeightUnit,
        @Param("dateCreated") dateCreated: OffsetDateTime,
        @Param("userId") userId: UUID,
        @Param("id") id: UUID
    )
}