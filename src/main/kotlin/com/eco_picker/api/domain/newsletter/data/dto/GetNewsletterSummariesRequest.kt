package com.eco_picker.api.domain.newsletter.data.dto

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import com.eco_picker.api.global.data.BaseListRequest
import io.swagger.v3.oas.annotations.media.Schema

data class GetNewsletterSummariesRequest(
    @field:Schema(description = "category", defaultValue = "NEWS", nullable = true)
    val category: NewsletterCategory? = NewsletterCategory.NEWS,
) : BaseListRequest()
