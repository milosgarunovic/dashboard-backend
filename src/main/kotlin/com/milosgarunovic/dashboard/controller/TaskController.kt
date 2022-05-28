package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.api.TaskAddRequest
import com.milosgarunovic.dashboard.api.TaskResponse
import com.milosgarunovic.dashboard.api.TaskUpdateRequest
import com.milosgarunovic.dashboard.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/task")
class TaskController(val taskService: TaskService) {

    @GetMapping(produces = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    fun getAll(): List<TaskResponse> {
        return taskService.getAll()
    }

    @GetMapping("/{id}", produces = ["application/json"])
    fun getById(@PathVariable id: String): ResponseEntity<TaskResponse> {
        val task = taskService.getById(id)
        if (task != null) {
            return ResponseEntity(task, HttpStatus.OK)
        }
        return ResponseEntity(null, HttpStatus.NOT_FOUND)
    }

    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@RequestBody task: TaskAddRequest): TaskResponse {
        return taskService.add(task)
    }

    @PostMapping("/update", consumes = ["application/json"], produces = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    fun update(@RequestBody task: TaskUpdateRequest): TaskResponse {
        return taskService.update(task)
    }

    @DeleteMapping("/{id}", produces = ["application/json"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) {
        taskService.delete(id)
    }

    @PostMapping("/complete/{id}", consumes = ["application/json"], produces = ["application/json"])
    @ResponseStatus(HttpStatus.OK)
    fun complete(@PathVariable id: String) {
        taskService.complete(id)
    }
}