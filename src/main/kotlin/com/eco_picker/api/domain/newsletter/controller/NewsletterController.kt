package com.eco_picker.api.domain.newsletter.controller

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import com.eco_picker.api.domain.newsletter.data.dto.GetNewsletterResponse
import com.eco_picker.api.domain.newsletter.data.dto.GetNewsletterSummariesRequest
import com.eco_picker.api.domain.newsletter.data.dto.GetNewsletterSummariesResponse
import com.eco_picker.api.domain.newsletter.data.dto.GetRandomNewsletterSummaryResponse
import com.eco_picker.api.domain.newsletter.service.NewsletterService
import com.eco_picker.api.global.data.UserPrincipal
import com.eco_picker.api.global.document.OpenAPIConfig.Companion.JWT
import com.eco_picker.api.global.document.OperationTag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.ZonedDateTime

@RestController
class NewsletterController(private val newsletterService: NewsletterService) {
    @Operation(
        tags = [OperationTag.NEWSLETTER],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get an random newsletter summary",
    )
    @GetMapping("/random_newsletter_summary")
    fun getRandomNewsletterSummary(): GetRandomNewsletterSummaryResponse {
        val newsletterSummary = this.newsletterService.getRandomNewsletterSummary()
            ?: return GetRandomNewsletterSummaryResponse().apply {
                message = "Not found newsletter summary"
            }
        return GetRandomNewsletterSummaryResponse(newsletterSummary = newsletterSummary).apply {
            result = true
        }
    }

    @Operation(
        tags = [OperationTag.NEWSLETTER],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get newsletter summaries",
    )
    @PostMapping("/newsletter_summaries")
    fun getNewsletterSummaries(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody getNewsletterSummariesRequest: GetNewsletterSummariesRequest,
    ): GetNewsletterSummariesResponse {
        val category = getNewsletterSummariesRequest.category?.let { NewsletterCategory.valueOf(it.uppercase()) }
        return this.newsletterService.getNewsletterSummaries(
            offset = getNewsletterSummariesRequest.offset,
            limit = getNewsletterSummariesRequest.limit,
            category = category
        )
    }

    @Operation(
        tags = [OperationTag.NEWSLETTER],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get a newsletter",
    )
    @GetMapping("/newsletter/{id}")
    fun getEvent(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable id: Long,
    ): GetNewsletterResponse {
        val newsletter = this.newsletterService.getNewsletter(id = id) ?: return GetNewsletterResponse().apply {
            message = "Not found newsletter"
        }
        return GetNewsletterResponse(newsletter = newsletter).apply {
            result = true
        }
    }

    @Operation(
        tags = [OperationTag.NEWSLETTER],
        security = [SecurityRequirement(name = JWT)],
        summary = "Generate newsletters",
    )
    @PostMapping("/generate_newsletters")
    fun generateNewsletters(
        @RequestParam category: String,
        @RequestParam n: Int
    ): Map<String, Any> {
        return try {
            val newsletterCategory = NewsletterCategory.valueOf(category.uppercase())
            val generatedNewsletters = newsletterService.generateNewsletter(newsletterCategory, n)
            mapOf(
                "result" to true,
                "timestamp" to ZonedDateTime.now(),
                "generatedCount" to generatedNewsletters.size,
                "newsletters" to generatedNewsletters
            )
        } catch (e: Exception) {
            mapOf(
                "result" to false,
                "message" to "Failed to generate newsletters: ${e.message}",
                "code" to "GENERATION_ERROR",
                "timestamp" to ZonedDateTime.now()
            )
        }
    }
}