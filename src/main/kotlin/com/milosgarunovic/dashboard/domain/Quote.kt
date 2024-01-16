package com.milosgarunovic.dashboard.domain

class Quote(
    val quote: String, // this is the quote itself
    val author: List<Author>? = null,
    val note: String? = null, // any note, thoughts about the quote or similar
    val source: String? = null, // source url, book and page number or something like that
) : BaseEntity()