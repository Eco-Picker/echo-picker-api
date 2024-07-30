package com.eco_picker.api.domain.newsletter.data.dto

import com.eco_picker.api.domain.newsletter.data.NewsletterSummary
import com.eco_picker.api.global.data.DefaultResponse
import io.swagger.v3.oas.annotations.media.Schema

data class GetRandomNewsletterSummaryResponse(
    @field:Schema(description = "newsletter summary")
    val newsletterSummary: NewsletterSummary
) : DefaultResponse()

