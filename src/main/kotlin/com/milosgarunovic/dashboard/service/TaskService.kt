package com.milosgarunovic.dashboard.service

import com.milosgarunovic.dashboard.api.TaskAddRequest
import com.milosgarunovic.dashboard.api.TaskResponse
import com.milosgarunovic.dashboard.api.TaskUpdateRequest
import com.milosgarunovic.dashboard.api.toTask
import com.milosgarunovic.dashboard.domain.toTaskResponse
import com.milosgarunovic.dashboard.repository.TaskRepository
import org.springframework.stereotype.Component

@Component
class TaskService(
    val taskRepository: TaskRepository
) {

    fun getAll(): List<TaskResponse> {
        return taskRepository.findAll().map { it.toTaskResponse() }
    }

    fun getById(id: Long): TaskResponse? {
        val task = taskRepository.getTaskById(id)
        if (task != null) {
            return task.toTaskResponse()
        }
        return null
    }

    fun add(taskAddRequest: TaskAddRequest): TaskResponse {
        return taskRepository.save(taskAddRequest.toTask()).toTaskResponse()
    }

    fun update(taskUpdateRequest: TaskUpdateRequest): TaskResponse {
        return taskRepository.save(taskUpdateRequest.toTask()).toTaskResponse()
    }

    fun delete(id: Long) {
        taskRepository.deleteById(id)
    }

    fun complete(id: Long) {
        taskRepository.complete(id)
    }
}