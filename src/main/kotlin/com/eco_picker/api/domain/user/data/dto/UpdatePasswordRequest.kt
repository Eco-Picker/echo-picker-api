package com.eco_picker.api.domain.user.data.dto

import io.swagger.v3.oas.annotations.media.Schema

data class UpdatePasswordRequest(
    @field:Schema(description = "current password", required = true)
    val password: String,

    @field:Schema(description = "new password", required = true)
    val newPassword: String,
    
    @field:Schema(description = "confirmed new password", required = true)
    val confirmNewPassword: String,
)
