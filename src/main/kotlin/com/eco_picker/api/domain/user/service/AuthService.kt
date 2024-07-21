package com.eco_picker.api.domain.user.service

import com.eco_picker.api.domain.user.data.dto.*
import com.eco_picker.api.global.support.JwtManager
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtManager: JwtManager
) {
    fun signup(signupRequest: SignupRequest): SignupResponse {
        return SignupResponse().apply {
            result = true
        }
    }

    fun login(loginRequest: LoginRequest): LoginResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )
        val username = authentication.name
        val accessToken = jwtManager.generateAccessToken(username = username)
        val refreshToken = jwtManager.generateRefreshToken(username = username)

        return LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        ).apply {
            result = true
        }
    }

    fun renewAccessToken(refreshToken: String): String? {
        if (jwtManager.validateRefreshToken(refreshToken)) {
            val username = jwtManager.getUsernameFromRefreshToken(refreshToken)
            username?.let {
                val newAccessToken = jwtManager.generateAccessToken(it)
                return@let newAccessToken
            }
        }
        return null
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