package com.eco_picker.api.domain.user.data.dto

import com.eco_picker.api.global.data.BaseResponse
import io.swagger.v3.oas.annotations.media.Schema

data class LoginResponse(
    @field:Schema(description = "access Token", nullable = true)
    val accessToken: String? = null,
    @field:Schema(description = "refresh Token", nullable = true)
    val refreshToken: String? = null
) : BaseResponse<LoginResponse.Code>() {

    enum class Code {
        LOGIN_FAILED,
        EXCEED_LOGIN_TRY,
        EMAIL_NOT_VERIFIED,
    }
}
