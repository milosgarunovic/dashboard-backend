package com.milosgarunovic.dashboard.service

import com.milosgarunovic.dashboard.api.QuoteRequest
import com.milosgarunovic.dashboard.api.QuoteResponse
import com.milosgarunovic.dashboard.api.QuoteUpdateRequest
import com.milosgarunovic.dashboard.repository.QuoteRepository
import com.milosgarunovic.dashboard.spring.util.SecurityContextHolderUtil
import org.springframework.stereotype.Component

@Component
class QuoteService(
    val quoteRepository: QuoteRepository,
    val userService: UserService,
) {

    fun add(request: QuoteRequest) {
        val userId = SecurityContextHolderUtil.getUserId()
        val user = userService.getById(userId)
        val quote = request.toQuote(user)
        quoteRepository.save(quote)
    }

    fun delete(id: Long) {
        val userId = SecurityContextHolderUtil.getUserId()
        quoteRepository.deleteByIdAndUserId(id, userId)
    }

    fun get(): List<QuoteResponse> {
        val userId = SecurityContextHolderUtil.getUserId()
        return quoteRepository.getAllByUserId(userId).map { it.toQuoteResponse() }
    }

    fun update(request: QuoteUpdateRequest) {
        val userId = SecurityContextHolderUtil.getUserId()
        quoteRepository.update(
            request.quote,
            request.authors?.joinToString(","),
            request.note,
            request.source,
            userId,
            request.id
        )
    }

}