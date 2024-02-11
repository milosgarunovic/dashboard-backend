package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.api.QuoteRequest
import com.milosgarunovic.dashboard.api.QuoteResponse
import com.milosgarunovic.dashboard.api.QuoteUpdateRequest
import com.milosgarunovic.dashboard.service.QuoteService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/quotes")
class QuoteController(val quoteService: QuoteService) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@RequestBody request: QuoteRequest) {
        quoteService.add(request)
    }

    @DeleteMapping(path = ["/{id}"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        quoteService.delete(id)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun get(): List<QuoteResponse> {
        return quoteService.get()
    }

    @PatchMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody request: QuoteUpdateRequest) {
        quoteService.update(request)
    }

}