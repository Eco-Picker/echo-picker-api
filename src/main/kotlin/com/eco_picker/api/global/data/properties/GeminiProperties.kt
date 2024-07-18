package com.eco_picker.api.global.data.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "gemini")
data class GeminiProperties(
    var appKey: String = "",
)
