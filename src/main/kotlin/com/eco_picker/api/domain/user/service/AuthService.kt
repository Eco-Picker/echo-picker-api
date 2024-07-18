package com.eco_picker.api.domain.user.service

import com.eco_picker.api.domain.user.data.dto.*
import org.springframework.stereotype.Service

@Service
class AuthService {
    fun signup(signupRequest: SignupRequest): SignupResponse {
        return SignupResponse().apply {
            result = true
        }
    }

    fun login(loginRequest: LoginRequest): LoginResponse {
        return LoginResponse().apply {
            result = true
            jwtToken = "dummy"
        }
    }

    fun logout(): Boolean {
        return true
    }

    fun verifyMail(token: String): VerifyMailResponse {
        return VerifyMailResponse().apply {
            result = true
        }
    }

    fun sendTempPassword(): Boolean {
        val tempPassword = this.generateTempPassword()
        // @todo send mail
        return true
    }

    private fun generateTempPassword(): String {
        return "temp password"
    }
}