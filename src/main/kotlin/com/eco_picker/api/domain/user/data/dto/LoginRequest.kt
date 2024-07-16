package com.eco_picker.api.domain.user.data.dto


data class LoginRequest(
    val username: String,
    val password: String,
    val rememberMe: Boolean? = false,
    val rememberMeTime: Long? = 0L,
)
