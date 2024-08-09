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

    private val garbageScoreTable = mapOf(
        "plastic" to 1,
        "metal" to 2,
        "glass" to 3,
        "cardboard_paper" to 4,
        "food_scraps" to 5,
        "other" to 6
    )

    // Update the function to correctly capture and return newsletters
    fun generateNewsletter(category: NewsletterCategory, n: Int): List<Newsletter> {
        val generatedNewsletters = mutableListOf<Newsletter>()
        try {
            logger.debug { "Generating newsletter for category $category" }

            // Generate newsletters using the GeminiManager
            val newsletters = this.geminiManager.generateNewsletter(category = category, n = n)

            // Save each generated newsletter and capture it in the list
            newsletters.forEach { newsletter ->
                val savedNewsletter = newsletterRepository.save(
                    NewsletterEntity(
                        title = newsletter.title,
                        content = newsletter.content,
                        source = newsletter.source,
                        category = category,
                        publishedAt = newsletter.publishedAt
                    )
                )
                // Add the saved newsletter to the list with the database-generated ID
                generatedNewsletters.add(
                    Newsletter(
                        id = savedNewsletter.id!!,  // Ensure the ID from DB is captured
                        title = savedNewsletter.title,
                        content = savedNewsletter.content,
                        category = savedNewsletter.category,
                        source = savedNewsletter.source,
                        publishedAt = savedNewsletter.publishedAt
                    )
                )
            }
        } catch (e: Exception) {
            logger.error(e) { "Failed to generate newsletters" }
        }
        return generatedNewsletters
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
            val pageNumber = offset / limit

            val pageable = PageRequest.of(pageNumber, limit, Sort.by(Sort.Direction.DESC, "id"))
            val pages = if (category != null) {
                newsletterRepository.findAllByCategory(category, pageable)
            } else {
                newsletterRepository.findAll(pageable)
            }

            val currentPage = pageNumber + 1
            val totalItems = pages.totalElements
            val totalPages = (totalItems + limit - 1) / limit

            return GetNewsletterSummariesResponse(
                currentPage = currentPage,
                totalItems = totalItems,
                totalPages = totalPages.toInt(),
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