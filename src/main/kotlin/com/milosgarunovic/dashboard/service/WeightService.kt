package com.milosgarunovic.dashboard.service

import com.milosgarunovic.dashboard.api.WeightRequest
import com.milosgarunovic.dashboard.api.WeightResponse
import com.milosgarunovic.dashboard.api.toWeight
import com.milosgarunovic.dashboard.domain.toWeightResponse
import com.milosgarunovic.dashboard.repository.WeightRepository
import com.milosgarunovic.dashboard.spring.UsernamePasswordAuthentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class WeightService(
    val weightRepository: WeightRepository,
    val userService: UserService,
) {

    fun add(weightRequest: WeightRequest): List<WeightResponse> {
        val userAuth = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthentication
        val userId = userAuth.id
        val user = userService.getById(userId)
        weightRepository.save(weightRequest.toWeight(user))

        return weightRepository.findAllByUserIdOrderByDateCreatedDesc(userId).map { it.toWeightResponse() }
    }

    fun get(): List<WeightResponse> {
        val userAuth = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthentication
        return weightRepository.findAllByUserIdOrderByDateCreatedDesc(userAuth.id).map { it.toWeightResponse() }
    }

}