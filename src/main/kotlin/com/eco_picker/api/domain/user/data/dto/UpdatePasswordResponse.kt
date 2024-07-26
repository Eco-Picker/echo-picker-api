package com.eco_picker.api.domain.user.data.dto

import com.eco_picker.api.global.data.BaseResponse

class UpdatePasswordResponse : BaseResponse<UpdatePasswordResponse.Code>() {
    enum class Code {
        INVALID_PASSWORD,
        NEW_PASSWORD_IS_SAME_AS_CURRENT_PASSWORD,
        NOT_MATCHED_NEW_PASSWORDS,
        SUCCESS
    }
}