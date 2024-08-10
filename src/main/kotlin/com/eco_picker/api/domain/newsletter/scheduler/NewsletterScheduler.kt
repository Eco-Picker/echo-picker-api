package com.eco_picker.api.scheduler

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import com.eco_picker.api.domain.newsletter.service.NewsletterService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class NewsletterScheduler(private val newsletterService: NewsletterService) {

    // Scheduled for cron - every Saturday midnight
    @Scheduled(cron = "0 0 0 * * 6")
    fun generateWeeklyNewsletters() {
        try {
            newsletterService.generateNewsletter(NewsletterCategory.NEWS, 3)
            newsletterService.generateNewsletter(NewsletterCategory.EDUCATION, 3)
            newsletterService.generateNewsletter(NewsletterCategory.EVENT, 3)
            logger.info { "Scheduled newsletter generation executed successfully" }
        } catch (e: Exception) {
            logger.error(e) { "Error occurred during scheduled newsletter generation" }
        }
    }
}
