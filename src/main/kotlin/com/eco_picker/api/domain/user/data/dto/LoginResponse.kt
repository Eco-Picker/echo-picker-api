package com.eco_picker.api.domain.user.data.dto

import com.eco_picker.api.global.data.BaseResponse

class LoginResponse : BaseResponse<LoginResponse.Code>() {
    var jwtToken: String? = null
    enum class Code {
        LOGIN_FAILED,
        EXCEED_LOGIN_TRY,
        EMAIL_NOT_VERIFIED,
    }
}
