package com.milosgarunovic.dashboard.domain

import java.time.LocalDate

class Weight(
    val value: Double, // limit to some values and maximum of 1 decimal
    val date: LocalDate = LocalDate.now(),
)