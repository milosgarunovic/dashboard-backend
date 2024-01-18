package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.api.weight.WeightRequest
import com.milosgarunovic.dashboard.api.weight.WeightUpdateRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/weight")
class WeightTrackerController {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@RequestBody weightRequest: WeightRequest) {

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
    fun get() {

    }
}