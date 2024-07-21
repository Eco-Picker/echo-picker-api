package com.eco_picker.api.domain.user.data.dto

import io.swagger.v3.oas.annotations.media.Schema

data class RenewAccessTokenRequest(
    @field:Schema(description = "refresh token", required = true)
    val refreshToken: String
)
