package com.eco_picker.api.domain.user.data.dto

import io.swagger.v3.oas.annotations.media.Schema

data class SendTempPasswordRequest(
    @field:Schema(description = "email", required = true)
    val email: String
)
