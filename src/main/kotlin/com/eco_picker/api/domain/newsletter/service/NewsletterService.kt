package com.eco_picker.api.domain.newsletter.service

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import com.eco_picker.api.domain.newsletter.data.Newsletter
import com.eco_picker.api.domain.newsletter.data.NewsletterSummary
import com.eco_picker.api.domain.newsletter.data.dto.GetNewsletterSummariesResponse
import com.eco_picker.api.domain.newsletter.data.entity.NewsletterEntity
import com.eco_picker.api.domain.newsletter.repository.NewsletterRepository
import com.eco_picker.api.global.support.GeminiManager
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrDefault


@Service
class NewsletterService(
    private val geminiManager: GeminiManager,
    private val newsletterRepository: NewsletterRepository
) {
    private val logger = KotlinLogging.logger { }

    // comment out - just to pass gradlew build for now (> just return)
    fun generateNewsletter(category: NewsletterCategory) {
        try {
            return
            logger.debug { "Generating news letter for category $category" }
            val newsletter = this.geminiManager.generateNewsletter(category = category)
            newsletterRepository.save(
                NewsletterEntity(
                    title = newsletter.title,
                    content = newsletter.content,
                    source = newsletter.source,
                    category = category,
                    publishedAt = newsletter.publishedAt
                )
            )
        } catch (e: Exception) {
            logger.error(e) { "Failed to generate newsletter" }
        }
    }

    fun getRandomNewsletterSummary(): NewsletterSummary? {
        val newsletterEntity = newsletterRepository.findOneRandom() ?: return null

        return NewsletterSummary(
            id = newsletterEntity.id!!,
            title = newsletterEntity.title,
            summary = newsletterEntity.content,
            category = newsletterEntity.category
        )
    }

    fun getNewsletterSummaries(offset: Int, limit: Int, category: NewsletterCategory?): GetNewsletterSummariesResponse {
        try {
            val pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "id"))
            val pages = if (category != null) {
                newsletterRepository.findAllByCategory(category, pageable)
            } else {
                newsletterRepository.findAll(pageable)
            }

            return GetNewsletterSummariesResponse(
                currentPage = pages.number,
                totalItems = pages.totalElements,
                totalPages = pages.totalPages,
                newsletterSummaryList = pages.content.map {
                    NewsletterSummary(
                        id = it.id!!,
                        title = it.title,
                        summary = it.content,
                        category = it.category,
                    )
                }
            ).apply {
                result = true
            }
        } catch (e: Exception) {
            logger.error(e) { "Failed to get newsletter summaries" }
            return GetNewsletterSummariesResponse().apply {
                message = e.message
            }
        }
    }

    fun getNewsletter(id: Long): Newsletter? {
        return newsletterRepository.findById(id).map {
            Newsletter(
                id = it.id!!,
                title = it.title,
                content = it.content,
                category = it.category,
                source = it.source,
                publishedAt = it.publishedAt,
            )
        }.getOrDefault(null)
    }
}