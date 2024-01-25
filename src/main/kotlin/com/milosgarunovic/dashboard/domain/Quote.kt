package com.milosgarunovic.dashboard.domain

import com.milosgarunovic.dashboard.domain.`typealias`.Markdown

class Quote(
    val quote: Markdown, // this is the quote itself
    val author: List<Author>? = null,
    val note: Markdown? = null, // any note, thoughts about the quote or similar
    val source: String? = null, // source url, book and page number or something like that
)/* : BaseEntity()*/