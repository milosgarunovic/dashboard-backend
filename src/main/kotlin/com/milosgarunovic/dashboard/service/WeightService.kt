package com.milosgarunovic.dashboard.service

import com.milosgarunovic.dashboard.api.WeightRequest
import com.milosgarunovic.dashboard.api.WeightResponse
import com.milosgarunovic.dashboard.api.WeightUpdateRequest
import com.milosgarunovic.dashboard.repository.WeightRepository
import com.milosgarunovic.dashboard.spring.util.SecurityContextHolderUtil
import org.springframework.stereotype.Component

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

        return get(userId)
    }

    fun get(userId: Long): List<WeightResponse> {
        return weightRepository.findAllByUserIdOrderByDateCreatedDesc(userId).map { it.toWeightResponse() }
    }

    fun get(): List<WeightResponse> {
        return get(SecurityContextHolderUtil.getUserId())
    }

    fun delete(id: Long) {
        weightRepository.deleteByIdAndUserId(id, SecurityContextHolderUtil.getUserId())
    }

    fun update(weight: WeightUpdateRequest): List<WeightResponse> {
        val userId = SecurityContextHolderUtil.getUserId()

        weightRepository.update(weight.value!!, weight.unit, weight.date!!, userId, weight.id!!)

        return get(userId)
    }

}