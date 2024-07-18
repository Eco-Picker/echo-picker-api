package com.eco_picker.api.domain.user.data.dto

import io.swagger.v3.oas.annotations.media.Schema

data class LoginRequest(
    @field:Schema(description = "username", required = true, example = "Jane")
    val username: String,

    @field:Schema(description = "password", required = true, example = "1q2w3e4rA1345!+")
    val password: String,

    @field:Schema(description = "remember me", nullable = true, defaultValue = "false")
    val rememberMe: Boolean? = false,

    @field:Schema(description = "remember me duration", nullable = true, defaultValue = "0")
    val rememberMeDuration: Long? = 0L,
)
