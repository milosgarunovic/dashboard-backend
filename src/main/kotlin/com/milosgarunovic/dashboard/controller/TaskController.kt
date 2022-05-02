package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.domain.Task
import com.milosgarunovic.dashboard.repository.TaskRepositoryImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/task")
class TaskController(val taskRepositoryImpl: TaskRepositoryImpl) {

    @GetMapping(produces = ["application/json"])
    fun getAll(): List<Task> {
        return taskRepositoryImpl.getAll()
    }

    @GetMapping("/{id}", produces = ["application/json"])
    fun getById(@PathVariable id: String): ResponseEntity<Task> {
        val task = taskRepositoryImpl.getById(id)
        if (task != null) {
            return ResponseEntity(task, HttpStatus.OK)
        }
        return ResponseEntity(null, HttpStatus.NOT_FOUND)
    }

    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@RequestBody task: Task): Task {
        return taskRepositoryImpl.add(task)
    }

    @PostMapping("/update", consumes = ["application/json"], produces = ["application/json"])
    fun update(@RequestBody task: Task): Task {
        return taskRepositoryImpl.update(task)
    }

    @DeleteMapping("/{id}", produces = ["application/json"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: String) {
        taskRepositoryImpl.delete(id)
    }
}