package com.eco_picker.api.domain.newsletter.data

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import io.swagger.v3.oas.annotations.media.Schema
import java.time.ZonedDateTime

data class Newsletter(
    @field:Schema(description = "newsletter ID (PK)")
    val id: Long,

    @field:Schema(description = "title")
    val title: String,

    @field:Schema(description = "content")
    val content: String,

    @field:Schema(description = "category")
    val category: NewsletterCategory,

    @field:Schema(description = "source")
    val source: String,

    @field:Schema(description = "published datetime")
    val publishedAt: ZonedDateTime = ZonedDateTime.now(),

    @field:Schema(description = "author", nullable = true)
    val author: String? = null,

    @field:Schema(description = "expired datetime", nullable = true)
    val expiredAt: ZonedDateTime? = null,
)
