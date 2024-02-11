package com.milosgarunovic.dashboard.repository

import com.milosgarunovic.dashboard.domain.Quote
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional

interface QuoteRepository : CrudRepository<Quote, Long> {

    @Transactional
    fun deleteByIdAndUserId(id: Long, userId: Long)

    fun getAllByUserId(userId: Long): List<Quote>

}