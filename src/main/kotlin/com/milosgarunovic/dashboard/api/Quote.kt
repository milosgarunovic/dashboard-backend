package com.milosgarunovic.dashboard.api

import com.milosgarunovic.dashboard.domain.Quote
import com.milosgarunovic.dashboard.domain.User
import com.milosgarunovic.dashboard.domain.`typealias`.Markdown
import com.milosgarunovic.dashboard.util.IdGenerator

class QuoteRequest(
    val quote: Markdown,
    val authors: List<String>?,
    val note: Markdown?,
    val source: String?,
) {

    fun toQuote(user: User): Quote {
        return Quote(IdGenerator.longId(), quote, authors?.joinToString(","), note, source, user)
    }

}

class QuoteResponse(
    val id: Long,
    val quote: Markdown,
    val note: Markdown?,
    val source: String?,
    val authors: List<String>?
)