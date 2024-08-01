package com.eco_picker.api.domain.user.data

import com.eco_picker.api.domain.user.constant.OnboardingStatus
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "User Info")
data class UserInfo(
    @field:Schema(description = "PK", required = true)
    val id: Long,

    @field:Schema(description = "username", required = true)
    val username: String,

    @field:Schema(description = "email", required = true)
    val email: String,

    @field:Schema(description = "onboarding status", required = true, defaultValue = "BEGIN", example = "BEGIN")
    val onboardingStatus: OnboardingStatus = OnboardingStatus.BEGIN
)
