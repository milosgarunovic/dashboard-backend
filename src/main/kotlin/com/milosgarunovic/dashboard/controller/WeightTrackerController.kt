package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.api.WeightRequest
import com.milosgarunovic.dashboard.api.WeightResponse
import com.milosgarunovic.dashboard.api.WeightUpdateRequest
import com.milosgarunovic.dashboard.service.WeightService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/weight")
class WeightTrackerController(
    val weightService: WeightService,
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@RequestBody weightRequest: WeightRequest): List<WeightResponse> {
        return weightService.add(weightRequest)
    }

    @PatchMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody weightUpdateRequest: WeightUpdateRequest) {

    }

    @DeleteMapping(path = ["/{id}"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) {

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun get(): List<WeightResponse> {
        return weightService.get()
    }
}