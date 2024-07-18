package com.eco_picker.api.domain.user.service

import com.eco_picker.api.domain.user.data.UserInfo
import com.eco_picker.api.domain.user.data.dto.UpdatePasswordRequest
import com.eco_picker.api.domain.user.data.dto.UpdatePasswordResponse
import org.springframework.stereotype.Service

@Service
class UserService {
    fun getInfo(): UserInfo {
        return UserInfo(id = 1, username = "mihee", email = "test@test.com")
    }

    fun updatePassword(updatePasswordRequest: UpdatePasswordRequest): UpdatePasswordResponse {
        return UpdatePasswordResponse().apply {
            result = true
        }
    }
}