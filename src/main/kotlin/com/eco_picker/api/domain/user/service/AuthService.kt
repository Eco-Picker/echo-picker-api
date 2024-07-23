package com.eco_picker.api.domain.user.service

import com.eco_picker.api.domain.mail.service.MailService
import com.eco_picker.api.domain.user.data.dto.*
import com.eco_picker.api.global.support.JwtManager
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtManager: JwtManager,
    private val mailService: MailService
) {
    fun signup(signupRequest: SignupRequest): SignupResponse {
        val (username, password, email) = signupRequest
        // @todo validate
        // @todo insert
        // @todo send a mail
        val token = ""
        mailService.sendVerify(username = username, email = email, token = token)
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

    fun renewAccessToken(userId: Long, refreshToken: String): String? {
        // @todo db 에서도 유효성 검사 (by userId, token)
        if (jwtManager.validateRefreshToken(refreshToken)) {
            val username = jwtManager.getUsernameFromRefreshToken(refreshToken)
            username?.let {
                val newAccessToken = jwtManager.generateAccessToken(it)
                return@let newAccessToken
            }
        }
        return null
    }

    fun logout(userId: Long): Boolean {
        return true
    }

    fun verifyMail(token: String): VerifyMailResponse {
        return VerifyMailResponse().apply {
            result = true
        }
    }

    fun sendTempPassword(email: String): Boolean {
        val tempPassword = this.generateTempPassword()
        val username = "" // @todo get from db
        // @todo send mail
        mailService.sendTempPassword(username = username, email = email, password = tempPassword)
        return true
    }

    private fun generateTempPassword(): String {
        return "temp password"
    }
}