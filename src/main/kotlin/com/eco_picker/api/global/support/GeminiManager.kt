package com.eco_picker.api.global.support

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import com.eco_picker.api.domain.newsletter.data.Newsletter
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class GeminiManager {
    fun generateNewsletter(category: NewsletterCategory): Newsletter {
        // @todo 기사 별 prompt 정규화
        return Newsletter(
            id = 1L,
            title = "",
            content = "",
            category = NewsletterCategory.NEWS,
            publishedAt = ZonedDateTime.now(),
            source = ""
        )
    }

    fun analyzeImage(): String {
        return "result"
    }
}