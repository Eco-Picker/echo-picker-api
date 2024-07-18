package com.eco_picker.api.domain.user.data.dto

import com.eco_picker.api.global.data.BaseResponse
import io.swagger.v3.oas.annotations.media.Schema

class LoginResponse : BaseResponse<LoginResponse.Code>() {
    @field:Schema(description = "JWT Token", nullable = true)
    var jwtToken: String? = null

    enum class Code {
        LOGIN_FAILED,
        EXCEED_LOGIN_TRY,
        EMAIL_NOT_VERIFIED,
    }
}
