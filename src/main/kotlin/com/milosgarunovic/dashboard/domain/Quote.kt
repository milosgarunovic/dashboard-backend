package com.milosgarunovic.dashboard.domain

import com.milosgarunovic.dashboard.api.QuoteResponse
import com.milosgarunovic.dashboard.domain.`typealias`.Markdown
import org.hibernate.annotations.GenericGenerator
import javax.persistence.*

@Entity
@Table(name = "quotes")
class Quote(
    @Id
    @GeneratedValue(generator = "tsid")
    @GenericGenerator(name = "tsid", strategy = "io.hypersistence.utils.hibernate.id.TsidGenerator")
    val id: Long,

    val quote: Markdown, // this is the quote itself

    @Transient
    val author: List<Author>? = null,

    val note: Markdown? = null, // any note, thoughts about the quote or similar

    val source: String? = null, // source url, book and page number or something like that

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,
) {
    fun toQuoteResponse(): QuoteResponse {
        return QuoteResponse(id, quote, note, source)
    }
}