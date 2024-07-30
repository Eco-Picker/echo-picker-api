package com.eco_picker.api.domain.newsletter.data.dto

import com.eco_picker.api.domain.newsletter.data.Newsletter
import com.eco_picker.api.global.data.DefaultResponse

data class GetNewsletterResponse(val newsletter: Newsletter? = null) : DefaultResponse()
