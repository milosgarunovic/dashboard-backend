package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.api.WeightRequest
import com.milosgarunovic.dashboard.api.WeightResponse
import com.milosgarunovic.dashboard.api.WeightUpdateRequest
import com.milosgarunovic.dashboard.api.validation.onFailure
import com.milosgarunovic.dashboard.service.WeightService
import com.milosgarunovic.dashboard.spring.exception.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/weight")
class WeightController(
    val weightService: WeightService,
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@RequestBody weightRequest: WeightRequest): ResponseEntity<List<WeightResponse>> {
        weightRequest.validate().onFailure { throw BadRequestException(it) }

        val weights = weightService.add(weightRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(weights)
    }

    @PatchMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody weightUpdateRequest: WeightUpdateRequest): List<WeightResponse> {
        weightUpdateRequest.validate().onFailure { throw BadRequestException(it) }
        return weightService.update(weightUpdateRequest)
    }

    @DeleteMapping(path = ["/{id}"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) {
        weightService.delete(id)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun get(): List<WeightResponse> {
        return weightService.get()
    }
}