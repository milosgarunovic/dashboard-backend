package com.milosgarunovic.dashboard.repository

import com.milosgarunovic.dashboard.domain.Task

interface TaskRepository {
    fun add(task: Task): Task
    fun update(task: Task): Task
    fun delete(task: Task): Task
    fun getAll(): List<Task>
    fun getById(id: String): Task?
}