package com.milosgarunovic.dashboard.service

import com.milosgarunovic.dashboard.api.TaskAddRequest
import com.milosgarunovic.dashboard.api.TaskResponse
import com.milosgarunovic.dashboard.api.TaskUpdateRequest
import com.milosgarunovic.dashboard.api.toTask
import com.milosgarunovic.dashboard.domain.toTaskResponse
import com.milosgarunovic.dashboard.repository.TaskRepositoryImpl
import org.springframework.stereotype.Component

@Component
class TaskService(
    val taskRepositoryImpl: TaskRepositoryImpl
) {

    fun getAll(): List<TaskResponse> {
        return taskRepositoryImpl.getAll().map { it.toTaskResponse() }
    }

    fun getById(id: String): TaskResponse? {
        val task = taskRepositoryImpl.getById(id)
        if (task != null) {
            return task.toTaskResponse()
        }
        return null
    }

    fun add(taskAddRequest: TaskAddRequest): TaskResponse {
        return taskRepositoryImpl.add(taskAddRequest.toTask()).toTaskResponse()
    }

    fun update(taskUpdateRequest: TaskUpdateRequest): TaskResponse {
        return taskRepositoryImpl.update(taskUpdateRequest.toTask()).toTaskResponse()
    }

    fun delete(id: String) {
        taskRepositoryImpl.delete(id)
    }

    fun complete(id: String) {
        taskRepositoryImpl.complete(id)
    }
}