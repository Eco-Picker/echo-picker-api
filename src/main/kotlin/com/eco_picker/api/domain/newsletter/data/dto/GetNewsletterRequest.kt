package com.eco_picker.api.domain.newsletter.data.dto

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import io.swagger.v3.oas.annotations.media.Schema

data class GetNewsletterRequest(
    @field:Schema(description = "newsletter ID (PK)")
    val id: Long,

    @field:Schema(description = "category", defaultValue = "NEWS", nullable = true)
    val category: NewsletterCategory? = NewsletterCategory.NEWS,
)
