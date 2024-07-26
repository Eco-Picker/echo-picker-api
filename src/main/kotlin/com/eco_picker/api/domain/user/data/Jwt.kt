package com.eco_picker.api.domain.user.data

import java.time.ZonedDateTime

data class Jwt(
    val token: String,
    val expiresAt: ZonedDateTime,
    val issuedAt: ZonedDateTime,
)
