package com.eco_picker.api.domain.user.data.dto

import com.eco_picker.api.domain.user.data.UserInfo
import com.eco_picker.api.global.data.DefaultResponse

class UserInfoResponse : DefaultResponse() {
    var userInfo: UserInfo? = null
}