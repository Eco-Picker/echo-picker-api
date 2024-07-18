package com.eco_picker.api.domain.newsletter.controller

import com.eco_picker.api.domain.newsletter.service.NewsletterService
import org.springframework.web.bind.annotation.RestController

@RestController("/newsletter")
class NewsletterController(private val newsletterService: NewsletterService) {
    fun publish() {
        this.newsletterService.publish()
    }

    fun getNewsletters() {
        this.newsletterService.getNewsletters()
    }
}