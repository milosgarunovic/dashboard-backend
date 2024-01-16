package com.milosgarunovic.dashboard.domain

import kotlinx.datetime.LocalDateTime

class BookBorrowed(
    val borrowed: Boolean = false,
    val borrowedDate: LocalDateTime? = null,
    val borrowedTo: String?,
)