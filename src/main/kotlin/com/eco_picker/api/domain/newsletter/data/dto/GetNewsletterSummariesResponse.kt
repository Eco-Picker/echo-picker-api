package com.eco_picker.api.domain.newsletter.data.dto


import com.eco_picker.api.domain.newsletter.data.NewsletterSummary
import com.eco_picker.api.global.data.DefaultResponse
import io.swagger.v3.oas.annotations.media.Schema

data class GetNewsletterSummariesResponse(
    @field:Schema(description = "total items count")
    val totalItems: Long? = 0,

    @field:Schema(description = "total pages count")
    val totalPages: Int? = 0,

    @field:Schema(description = "current page")
    val currentPage: Int? = 1,

    @field:Schema(description = "newsletter items for list")
    val newsletterSummaryList: List<NewsletterSummary> = emptyList()
) : DefaultResponse()
