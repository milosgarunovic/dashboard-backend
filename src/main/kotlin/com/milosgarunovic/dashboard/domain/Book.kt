package com.milosgarunovic.dashboard.domain

import kotlinx.datetime.LocalDateTime

class Book(
    val title: String,
    val author: List<Author>? = null, // many books can have the same author
    val isRead: Boolean = false,
    val note: String? = null,
    val rating: Rating?,
    val publisher: String? = null,
    val borrowed: Boolean = false,
    val borrowedDate: LocalDateTime? = null,
) : BaseEntity()