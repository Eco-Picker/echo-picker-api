package com.eco_picker.api.domain.user.service

import com.eco_picker.api.domain.user.data.dto.LoginRequest
import org.springframework.stereotype.Service

@Service
class AuthService {
    fun signup() {}
    fun login(loginRequest: LoginRequest): String {
        return "dummy token - this is example";
    }

    fun logout() {}
    fun verifyMail() {}
}