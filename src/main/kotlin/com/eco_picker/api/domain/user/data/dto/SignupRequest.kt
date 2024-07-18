package com.eco_picker.api.domain.user.data.dto

import io.swagger.v3.oas.annotations.media.Schema

data class SignupRequest(
    @field:Schema(description = "username", required = true, example = "Jane")
    val username: String,

    @field:Schema(description = "password", required = true, example = "password")
    val password: String,

    @field:Schema(description = "email", required = true, example = "test@test.com")
    val email: String,
)
