package com.eco_picker.api.domain.user.data

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "User Info")
data class UserInfo(
    @field:Schema(description = "PK", required = true)
    val id: Long,

    @field:Schema(description = "username", required = true)
    val username: String,

    @field:Schema(description = "email", required = true)
    val email: String,
)
