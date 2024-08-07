package com.eco_picker.api.domain.garbage.data.dto

import com.eco_picker.api.domain.garbage.data.GeminiGarbage
import java.time.ZonedDateTime

data class AnalyzeGarbageResponse(
    val result: Boolean,
    val garbage: GeminiGarbage?,
    val message: String? = null, // Optional, present only if an error occurs
    val code: String? = null, // Optional, present only if an error occurs
    val timestamp: ZonedDateTime
)
