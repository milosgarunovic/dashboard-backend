package com.milosgarunovic.dashboard.service

import com.milosgarunovic.dashboard.domain.Task
import com.milosgarunovic.dashboard.repository.TaskRepositoryImpl
import org.springframework.stereotype.Component

@Component
class TaskService(
    val taskRepositoryImpl: TaskRepositoryImpl
) {

    fun getAll(): List<Task> {
        return taskRepositoryImpl.getAll()
    }

    fun getById(id: String): Task? {
        return taskRepositoryImpl.getById(id)
    }

    fun add(task: Task) : Task {
        return taskRepositoryImpl.add(task)
    }

    fun update(task: Task) : Task {
        return taskRepositoryImpl.update(task)
    }

    fun delete(id: String) {
        taskRepositoryImpl.delete(id)
    }
}