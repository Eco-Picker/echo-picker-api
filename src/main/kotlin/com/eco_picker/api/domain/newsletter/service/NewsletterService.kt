package com.eco_picker.api.domain.newsletter.service

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import com.eco_picker.api.domain.newsletter.data.Newsletter
import com.eco_picker.api.domain.newsletter.data.NewsletterListItem
import com.eco_picker.api.global.support.GeminiManager
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class NewsletterService(private val geminiManager: GeminiManager) {
    private fun generateNewsletter(category: NewsletterCategory): Boolean {
        val newsletter = this.geminiManager.generateNewsletter(category = category)
        // @todo save to dv
        return true
    }

    fun getRandomNewsletterId(): Long {
        return 1L;
    }

    fun getEducationalContents(offset: Int, limit: Int): List<NewsletterListItem> {
        return listOf(
            NewsletterListItem(
                id = 1L,
                title = "The Importance of Recycling",
                summary = "Learn why recycling is crucial for reducing waste and conserving resources. This guide covers the benefits of recycling and how to properly sort materials.",
                category = NewsletterCategory.EDUCATION,
            ),
            NewsletterListItem(
                id = 2L,
                title = "How to Reduce Plastic Waste",
                summary = "Discover practical tips for minimizing plastic use in your daily life. This article provides strategies for reducing plastic waste and opting for sustainable alternatives.",
                category = NewsletterCategory.EDUCATION,
            )
        )
    }

    fun getNewsList(offset: Int, limit: Int): List<NewsletterListItem> {
        return listOf(
            NewsletterListItem(
                id = 3L,
                title = "New Waste Sorting Regulations Announced",
                summary = "Local authorities have introduced new regulations for waste sorting to improve recycling rates. Learn about the changes and how they will impact your recycling efforts.",
                category = NewsletterCategory.NEWS,
            ),
            NewsletterListItem(
                id = 4L,
                title = "Plastic Waste Reduction Goals Met",
                summary = "A recent report shows that the city has met its plastic waste reduction goals ahead of schedule. Find out how these achievements were accomplished and what comes next.",
                category = NewsletterCategory.NEWS,
            )
        )
    }

    fun getEvents(offset: Int, limit: Int): List<NewsletterListItem> {
        return listOf(
            NewsletterListItem(
                id = 5L,
                title = "Community Clean-Up Day",
                summary = "Join us for a community clean-up day to help collect litter and beautify our local parks. Volunteers will meet at the central park entrance at 9 AM.",
                category = NewsletterCategory.EVENT,
            ),
            NewsletterListItem(
                id = 6L,
                title = "Recycling Workshop: Best Practices",
                summary = "Attend our workshop to learn the best practices for effective recycling. The workshop will cover how to sort waste correctly and reduce contamination in recycling bins.",
                category = NewsletterCategory.EVENT,
            )
        )
    }

    fun getEducationalContent(id: Long): Newsletter {
        return Newsletter(
            id = 1L,
            title = "The Importance of Recycling",
            content = "Learn why recycling is crucial for reducing waste and conserving resources. This guide covers the benefits of recycling and how to properly sort materials.",
            category = NewsletterCategory.EDUCATION,
            author = "Emily Green",
            publishedAt = ZonedDateTime.now(),
            source = "Eco News"
        )
    }

    fun getNews(id: Long): Newsletter {
        return Newsletter(
            id = 3L,
            title = "New Waste Sorting Regulations Announced",
            content = "Local authorities have introduced new regulations for waste sorting to improve recycling rates. Learn about the changes and how they will impact your recycling efforts.",
            category = NewsletterCategory.NEWS,
            author = "Sarah White",
            publishedAt = ZonedDateTime.now(),
            source = "City News"
        )
    }

    fun getEvent(id: Long): Newsletter {
        return Newsletter(
            id = 5L,
            title = "Community Clean-Up Day",
            content = "Join us for a community clean-up day to help collect litter and beautify our local parks. Volunteers will meet at the central park entrance at 9 AM.",
            category = NewsletterCategory.EVENT,
            author = "Cleanup Organizer",
            publishedAt = ZonedDateTime.now(),
            source = "Local Events"
        )
    }

}