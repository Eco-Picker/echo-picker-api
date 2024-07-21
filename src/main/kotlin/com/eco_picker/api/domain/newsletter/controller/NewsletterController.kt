package com.eco_picker.api.domain.newsletter.controller

import com.eco_picker.api.domain.newsletter.data.dto.GetNewsLetterIdResponse
import com.eco_picker.api.domain.newsletter.data.dto.GetNewsletterResponse
import com.eco_picker.api.domain.newsletter.data.dto.GetNewslettersResponse
import com.eco_picker.api.domain.newsletter.service.NewsletterService
import com.eco_picker.api.global.data.BaseListRequest
import com.eco_picker.api.global.data.UserPrincipal
import com.eco_picker.api.global.document.OpenAPIConfig.Companion.JWT
import com.eco_picker.api.global.document.OperationTag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController()
class NewsletterController(private val newsletterService: NewsletterService) {
    @Operation(
        tags = [OperationTag.NEWSLETTER],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get an random newsletter id",
    )
    @GetMapping("/random_newsletter_id")
    fun getRandomNewsletterId(): GetNewsLetterIdResponse {
        val newsletterId = this.newsletterService.getRandomNewsletterId();
        return GetNewsLetterIdResponse(id = newsletterId).apply {
            result = true
        }
    }

    @Operation(
        tags = [OperationTag.NEWSLETTER],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get educational contents",
    )
    @PostMapping("/educational_contents")
    fun getEducationalContents(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody baseListRequest: BaseListRequest
    ): GetNewslettersResponse {
        val newsletters = this.newsletterService.getEducationalContents(
            offset = baseListRequest.offset,
            limit = baseListRequest.limit
        )
        return GetNewslettersResponse(newsletters = newsletters).apply {
            result = true
        }
    }

    @Operation(
        tags = [OperationTag.NEWSLETTER],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get an educational content",
    )
    @GetMapping("/educational_content/{id}")
    fun getEducationalContent(
        @PathVariable id: Long,
        @AuthenticationPrincipal principal: UserPrincipal
    ): GetNewsletterResponse {
        val newsletter = this.newsletterService.getEducationalContent(id)
        return GetNewsletterResponse(newsletter = newsletter).apply {
            result = true
        }
    }

    @Operation(
        tags = [OperationTag.NEWSLETTER],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get news list",
    )
    @PostMapping("/news_list")
    fun getNews(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody baseListRequest: BaseListRequest
    ): GetNewslettersResponse {
        val newsletters =
            this.newsletterService.getNewsList(offset = baseListRequest.offset, limit = baseListRequest.limit)
        return GetNewslettersResponse(newsletters = newsletters).apply {
            result = true
        }
    }

    @Operation(
        tags = [OperationTag.NEWSLETTER],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get a news",
    )
    @GetMapping("/news/{id}")
    fun getNews(@PathVariable id: Long, @AuthenticationPrincipal principal: UserPrincipal): GetNewsletterResponse {
        val newsletter = this.newsletterService.getNews(id)
        return GetNewsletterResponse(newsletter = newsletter).apply {
            result = true
        }
    }

    @Operation(
        tags = [OperationTag.NEWSLETTER],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get events",
    )
    @PostMapping("/events")
    fun getEvents(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody baseListRequest: BaseListRequest
    ): GetNewslettersResponse {
        val newsletters =
            this.newsletterService.getEvents(offset = baseListRequest.offset, limit = baseListRequest.limit)
        return GetNewslettersResponse(newsletters = newsletters).apply {
            result = true
        }
    }

    @Operation(
        tags = [OperationTag.NEWSLETTER],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get an event",
    )
    @GetMapping("/event/{id}")
    fun getEvent(@PathVariable id: Long, @AuthenticationPrincipal principal: UserPrincipal): GetNewsletterResponse {
        val newsletter = this.newsletterService.getEvent(id)
        return GetNewsletterResponse(newsletter = newsletter).apply {
            result = true
        }
    }

}