package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.api.TaskAddRequest
import com.milosgarunovic.dashboard.api.TaskResponse
import com.milosgarunovic.dashboard.api.TaskUpdateRequest
import com.milosgarunovic.dashboard.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/task")
class TaskController(val taskService: TaskService) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<TaskResponse> {
        return taskService.getAll()
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getById(@PathVariable id: UUID): ResponseEntity<TaskResponse> {
        val task = taskService.getById(id)
        if (task != null) {
            return ResponseEntity(task, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@Valid @RequestBody task: TaskAddRequest): TaskResponse {
        return taskService.add(task)
    }

    @PostMapping("/update", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody task: TaskUpdateRequest): TaskResponse {
        return taskService.update(task)
    }

    @DeleteMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID) {
        taskService.delete(id)
    }

    @PostMapping("/complete/{id}", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun complete(@PathVariable id: UUID) {
        taskService.complete(id)
    }
}