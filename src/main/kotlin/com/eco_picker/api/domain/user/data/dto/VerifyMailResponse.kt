package com.eco_picker.api.domain.user.data.dto

import com.eco_picker.api.global.data.BaseResponse
import io.swagger.v3.oas.annotations.media.Schema

class VerifyMailResponse : BaseResponse<VerifyMailResponse.Code>() {
    @field:Schema(description = "email")
    val email: String? = null

    enum class Code {
        ALREADY_VERIFIED_USER,
        EMAIL_TOKEN_EXPIRED,
        INVALIDATED_TOKEN,
    }
}