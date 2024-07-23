package com.eco_picker.api.global.data.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("general")
data class GeneralProperties(
    val apiBaseUrl: String = "",
    val appBaseUrl: String = "",
    val staticBaseUrl: String = "",
)
