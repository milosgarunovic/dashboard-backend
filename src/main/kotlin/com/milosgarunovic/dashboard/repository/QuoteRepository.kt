package com.milosgarunovic.dashboard.repository

import com.milosgarunovic.dashboard.domain.Quote
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface QuoteRepository : CrudRepository<Quote, Long> {

    @Transactional
    fun deleteByIdAndUserId(id: Long, userId: Long)

    fun getAllByUserId(userId: Long): List<Quote>

    @Transactional
    @Modifying
    @Query(
        """UPDATE Quote SET 
        quote = :quote,
        authors = :authors,
        note = :note,   
        source = :source
        WHERE user.id = :userId AND id = :id"""
    )
    fun update(
        @Param("quote") quote: String,
        @Param("authors") authors: String?,
        @Param("note") note: String?,
        @Param("source") source: String?,
        @Param("userId") userId: Long,
        @Param("id") id: Long
    )

}