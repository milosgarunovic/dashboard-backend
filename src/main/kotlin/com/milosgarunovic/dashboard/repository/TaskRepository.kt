package com.milosgarunovic.dashboard.repository

import com.milosgarunovic.dashboard.domain.Task
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
interface TaskRepository : CrudRepository<Task, Long> {

    fun getTaskById(id: Long): Task?

    @Modifying
    @Query("UPDATE tasks SET completed = true WHERE id = ?1")
    @Transactional
    fun complete(id: Long)

}