package com.eco_picker.api.domain.newsletter.service

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import com.eco_picker.api.domain.newsletter.data.Newsletter
import com.eco_picker.api.domain.newsletter.data.NewsletterSummary
import com.eco_picker.api.domain.newsletter.data.dto.GetNewsletterRequest
import com.eco_picker.api.domain.newsletter.data.dto.GetNewsletterSummariesRequest
import com.eco_picker.api.domain.newsletter.data.dto.GetNewsletterSummariesResponse
import com.eco_picker.api.domain.newsletter.repository.EducationalContentRepository
import com.eco_picker.api.domain.newsletter.repository.EventRepository
import com.eco_picker.api.domain.newsletter.repository.NewsRepository
import com.eco_picker.api.global.support.GeminiManager
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrDefault


@Service
class NewsletterService(
    private val geminiManager: GeminiManager,
    private val educationalContentRepository: EducationalContentRepository,
    private val eventRepository: EventRepository,
    private val newsRepository: NewsRepository
) {
    private val logger = KotlinLogging.logger { }

    private fun generateNewsletter(category: NewsletterCategory): Boolean {
        val newsletter = this.geminiManager.generateNewsletter(category = category)
        return true
    }

    fun getRandomNewsletterSummary(): NewsletterSummary {
        // @todo 이게 필요한가..
        return NewsletterSummary(
            id = 1L,
            title = "The Importance of Recycling",
            summary = "Learn why recycling is crucial for reducing waste and conserving resources. This guide covers the benefits of recycling and how to properly sort materials.",
            category = NewsletterCategory.EDUCATION,
        )
    }

    fun getNewsletterSummaries(params: GetNewsletterSummariesRequest): GetNewsletterSummariesResponse {
        try {
            val pageable = PageRequest.of(params.offset, params.limit, Sort.by(Sort.Direction.DESC, "id"))
            val response = when (params.category!!) {
                NewsletterCategory.NEWS -> {
                    this.getNewsSummaries(pageable = pageable)
                }

                NewsletterCategory.EVENT -> {
                    this.getEventSummaries(pageable = pageable)
                }

                NewsletterCategory.EDUCATION -> {
                    this.getEducationalContentSummaries(pageable = pageable)
                }
            }

            return response.apply {
                result = true
            }
        } catch (e: Exception) {
            logger.error(e) { "Failed to get newsletter summaries" }
            return GetNewsletterSummariesResponse().apply {
                message = e.message
            }
        }
    }

    fun getNewsletter(params: GetNewsletterRequest): Newsletter? {
        val (id, category) = params
        return when (category!!) {
            NewsletterCategory.NEWS -> this.getNews(id)
            NewsletterCategory.EVENT -> this.getEvent(id)
            NewsletterCategory.EDUCATION -> this.getEducationalContent(id)
        }
    }

    private fun getEducationalContentSummaries(pageable: Pageable): GetNewsletterSummariesResponse {
        val pages = educationalContentRepository.findAll(pageable)

        return GetNewsletterSummariesResponse(
            currentPage = pages.number,
            totalItems = pages.totalElements,
            totalPages = pages.totalPages,
            newsletterSummaryList = pages.content.map {
                NewsletterSummary(
                    id = it.id!!,
                    title = it.title,
                    summary = it.content,
                    category = NewsletterCategory.EDUCATION,
                )
            }
        )
    }

    private fun getNewsSummaries(pageable: Pageable): GetNewsletterSummariesResponse {
        val pages = newsRepository.findAll(pageable)

        return GetNewsletterSummariesResponse(
            currentPage = pages.number,
            totalItems = pages.totalElements,
            totalPages = pages.totalPages,
            newsletterSummaryList = pages.content.map {
                NewsletterSummary(
                    id = it.id!!,
                    title = it.title,
                    summary = it.content,
                    category = NewsletterCategory.NEWS,
                )
            }
        )
    }

    private fun getEventSummaries(pageable: Pageable): GetNewsletterSummariesResponse {
        val pages = eventRepository.findAll(pageable)

        return GetNewsletterSummariesResponse(
            currentPage = pages.number,
            totalItems = pages.totalElements,
            totalPages = pages.totalPages,
            newsletterSummaryList = pages.content.map {
                NewsletterSummary(
                    id = it.id!!,
                    title = it.title,
                    summary = it.content,
                    category = NewsletterCategory.EVENT,
                )
            }
        )
    }

    private fun getEducationalContent(id: Long): Newsletter? {
        return educationalContentRepository.findById(id).map {
            Newsletter(
                id = it.id!!,
                title = it.title,
                content = it.content,
                category = NewsletterCategory.EDUCATION,
                source = it.source,
                publishedAt = it.publishedAt,
            )
        }.getOrDefault(null)
    }

    private fun getNews(id: Long): Newsletter? {
        return newsRepository.findById(id).map {
            Newsletter(
                id = it.id!!,
                title = it.title,
                content = it.content,
                category = NewsletterCategory.NEWS,
                source = it.source,
                publishedAt = it.publishedAt,
            )
        }.getOrDefault(null)
    }

    private fun getEvent(id: Long): Newsletter? {
        return eventRepository.findById(id).map {
            Newsletter(
                id = it.id!!,
                title = it.title,
                content = it.content,
                category = NewsletterCategory.EVENT,
                source = it.source,
                startAt = it.startAt,
                endAt = it.endAt
            )
        }.getOrDefault(null)
    }

}