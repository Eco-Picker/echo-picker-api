package com.eco_picker.api.domain.user.data.dto

import com.eco_picker.api.global.validator.Password
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class SignupRequest(
    @field:Schema(description = "username", required = true, example = "Jane")
    @field:NotBlank
    val username: String,

    @field:Schema(description = "password", required = true, example = "password")
    @field:NotBlank
    @field:Password
    val password: String,

    @field:Schema(description = "email", required = true, example = "test@test.com")
    @field:Email
    val email: String,
)
