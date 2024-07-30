package com.eco_picker.api.domain.newsletter.data

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import io.swagger.v3.oas.annotations.media.Schema

data class NewsletterSummary(
    @field:Schema(description = "newsletter ID (PK)", required = true)
    val id: Long,

    @field:Schema(description = "title", required = true)
    val title: String,

    @field:Schema(description = "summary", required = true)
    val summary: String,

    @field:Schema(description = "category", required = true, example = "NEWS")
    val category: NewsletterCategory
)
