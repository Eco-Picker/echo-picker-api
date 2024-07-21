package com.eco_picker.api.domain.newsletter.data.dto


import com.eco_picker.api.domain.newsletter.data.NewsletterListItem
import com.eco_picker.api.global.data.DefaultResponse

data class GetNewslettersResponse(
    val newsletters: List<NewsletterListItem> = emptyList()
) : DefaultResponse()
