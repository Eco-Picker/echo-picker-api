package com.eco_picker.api.domain.newsletter.controller

import com.eco_picker.api.domain.newsletter.data.dto.*
import com.eco_picker.api.domain.newsletter.service.NewsletterService
import com.eco_picker.api.global.data.UserPrincipal
import com.eco_picker.api.global.document.OpenAPIConfig.Companion.JWT
import com.eco_picker.api.global.document.OperationTag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

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
        val newsletterSummaries =
            this.newsletterService.getNewsletterSummaries(params = getNewsletterSummariesRequest)
        return GetNewsletterSummariesResponse(newsletterSummaryList = newsletterSummaries).apply {
            result = true
        }
    }

    @Operation(
        tags = [OperationTag.NEWSLETTER],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get a newsletter",
    )
    @PostMapping("/newsletter")
    fun getEvent(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody getNewsletterRequest: GetNewsletterRequest
    ): GetNewsletterResponse {
        val newsletter = this.newsletterService.getNewsletter(params = getNewsletterRequest)
        return GetNewsletterResponse(newsletter = newsletter).apply {
            result = true
        }
    }

}