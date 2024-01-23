package com.milosgarunovic.dashboard.service

import com.milosgarunovic.dashboard.api.WeightRequest
import com.milosgarunovic.dashboard.api.WeightResponse
import com.milosgarunovic.dashboard.api.WeightUpdateRequest
import com.milosgarunovic.dashboard.api.toWeight
import com.milosgarunovic.dashboard.domain.toWeightResponse
import com.milosgarunovic.dashboard.repository.WeightRepository
import com.milosgarunovic.dashboard.spring.util.SecurityContextHolderUtil
import org.springframework.stereotype.Component
import java.util.*

@Component
class WeightService(
    val weightRepository: WeightRepository,
    val userService: UserService,
) {

    fun add(weightRequest: WeightRequest): List<WeightResponse> {
        val userAuth = SecurityContextHolderUtil.getAuth()
        val userId = userAuth.id
        val user = userService.getById(userId)
        weightRepository.save(weightRequest.toWeight(user))

        return get(userAuth.id)
    }

    fun get(userId: UUID): List<WeightResponse> {
        return weightRepository.findAllByUserIdOrderByDateCreatedDesc(userId).map { it.toWeightResponse() }
    }

    fun get(): List<WeightResponse> {
        val userAuth = SecurityContextHolderUtil.getAuth()
        return get(userAuth.id)
    }

    fun delete(id: UUID) {
        val userAuth = SecurityContextHolderUtil.getAuth()
        weightRepository.deleteByIdAndUserId(id, userAuth.id)
    }

    fun update(weight: WeightUpdateRequest): List<WeightResponse> {
        val userAuth = SecurityContextHolderUtil.getAuth()
        weightRepository.update(weight.value, weight.unit, weight.date, userAuth.id, weight.id)

        return get(userAuth.id)
    }

}