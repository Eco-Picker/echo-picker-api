package com.eco_picker.api.global.data.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("security.jwt")
data class JwtProperties(
    var issuer: String = "",
    var accessTokenValidity: Long = 0,  // milliseconds
    var refreshTokenValidity: Long = 0, // milliseconds
)