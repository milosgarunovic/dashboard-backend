package com.milosgarunovic.dashboard.repository

import com.milosgarunovic.dashboard.domain.Weight
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface WeightRepository : CrudRepository<Weight, UUID> {

    fun findAllByUserIdOrderByDateCreatedDesc(userId: UUID): List<Weight>

    @Transactional
    fun deleteByIdAndUserId(id: UUID, userId: UUID)
}