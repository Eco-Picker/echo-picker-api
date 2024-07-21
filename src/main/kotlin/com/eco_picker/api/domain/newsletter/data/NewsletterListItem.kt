package com.eco_picker.api.domain.newsletter.data

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import io.swagger.v3.oas.annotations.media.Schema
import java.time.ZonedDateTime

data class NewsletterListItem(
    @field:Schema(description = "newsletter ID (PK)")
    val id: Long,

    @field:Schema(description = "title")
    val title: String,

    @field:Schema(description = "summary")
    val summary: String,

    @field:Schema(description = "category")
    val category: NewsletterCategory,

    @field:Schema(description = "published datetime")
    val publishedAt: ZonedDateTime = ZonedDateTime.now(),
)
