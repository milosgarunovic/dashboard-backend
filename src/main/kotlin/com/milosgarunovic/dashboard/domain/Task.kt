package com.milosgarunovic.dashboard.domain

import com.milosgarunovic.dashboard.api.TaskResponse
import org.hibernate.annotations.GenericGenerator
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "tasks")
data class Task(
    @Id
    @GeneratedValue(generator = "tsid")
    @GenericGenerator(name = "tsid", strategy = "io.hypersistence.utils.hibernate.id.TsidGenerator")
    var id: Long,
    var name: String,
    var description: String? = null,
    var completed: Boolean = false,
//    val tags: List<String>?,
//    val userId: String, // user that is the owner
//    val color: String? = null,
) /*: BaseEntity()*/

fun Task.toTaskResponse() = TaskResponse(id, name, description, completed)