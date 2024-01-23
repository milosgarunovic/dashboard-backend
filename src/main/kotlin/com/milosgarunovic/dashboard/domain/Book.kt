package com.milosgarunovic.dashboard.domain

import com.milosgarunovic.dashboard.domain.BookStatus.NOT_READ
import kotlinx.datetime.LocalDateTime

class Book(
    val title: String,
    val author: List<Author>? = null, // many books can have the same author
    val status: BookStatus = NOT_READ,
    val own: Boolean = false, // does a user own a book, or it's in something like a wishlist
    val format: List<String>? = null, // list of formats like Paperback, pdf, epub, amazon/kindle
    val location: String? = null, // location of a book, or a link to private server
    val note: Markdown? = null,
    val rating: Rating?,
    val publisher: String? = null,
    val borrowed: BookBorrowed? = null,
    val publishingYear: Int? = null,
    val starred: Boolean = false, // like a bookmark
    // TODO tags and list of books, like user defined categories
) : BaseEntity()

class BookBorrowed(
    val borrowed: Boolean = false,
    val borrowedDate: LocalDateTime? = null,
    val borrowedTo: String?,
)

enum class BookStatus {
    NOT_READ,
    IN_PROGRESS,
    READ,
}