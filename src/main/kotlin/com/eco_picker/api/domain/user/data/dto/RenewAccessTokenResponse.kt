package com.eco_picker.api.domain.user.data.dto

import com.eco_picker.api.global.data.BaseResponse

class RenewAccessTokenResponse(val accessToken: String? = null) : BaseResponse<RenewAccessTokenResponse.Code>() {
    enum class Code {
        INVALID_REFRESH_TOKEN,
        EXPIRED_REFRESH_TOKEN,
    }
}