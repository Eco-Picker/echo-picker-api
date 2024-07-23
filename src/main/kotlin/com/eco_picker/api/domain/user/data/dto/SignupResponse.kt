package com.eco_picker.api.domain.user.data.dto

import com.eco_picker.api.global.data.BaseResponse

class SignupResponse : BaseResponse<SignupResponse.Code>() {
    enum class Code {
        ALREADY_REGISTERED_EMAIL,
        ALREADY_REGISTERED_USERNAME,
        PENDING_VERIFY_EMAIL,
    }
}
