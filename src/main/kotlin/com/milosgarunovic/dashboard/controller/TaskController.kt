package com.milosgarunovic.dashboard.controller

import com.milosgarunovic.dashboard.domain.Task
import com.milosgarunovic.dashboard.repository.TaskRepositoryImpl
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/task")
class TaskController(val taskRepositoryImpl: TaskRepositoryImpl) {

    @GetMapping
    fun getAll(): List<Task> {
        return taskRepositoryImpl.getAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): Task? {
        return taskRepositoryImpl.getById(id)
    }

    @PostMapping
    fun add(@RequestBody task: Task): Task {
        return taskRepositoryImpl.add(task)
    }

    @PostMapping("/update")
    fun update(@RequestBody task: Task): Task {
        return taskRepositoryImpl.update(task)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String) {
        taskRepositoryImpl.delete(id)
    }
}