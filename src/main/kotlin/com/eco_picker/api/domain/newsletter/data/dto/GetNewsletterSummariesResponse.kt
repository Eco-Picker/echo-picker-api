package com.eco_picker.api.domain.newsletter.data.dto


import com.eco_picker.api.domain.newsletter.data.NewsletterSummary
import com.eco_picker.api.global.data.DefaultResponse
import io.swagger.v3.oas.annotations.media.Schema

data class GetNewsletterSummariesResponse(
    @field:Schema(description = "newsletter items for list")
    val newsletterSummaryList: List<NewsletterSummary> = emptyList()
) : DefaultResponse()
