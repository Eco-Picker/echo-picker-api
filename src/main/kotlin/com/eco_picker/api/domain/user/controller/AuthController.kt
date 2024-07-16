package com.eco_picker.api.domain.user.controller

import com.eco_picker.api.domain.user.data.dto.LoginRequest
import com.eco_picker.api.domain.user.data.dto.LoginResponse
import com.eco_picker.api.domain.user.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val authService: AuthService) {
    @PostMapping("/p/auth/signup")
    fun signup() {
        authService.signup()
    }

    /**
     * Request, Response Example
     */
    @PostMapping("/p/auth/login")
    fun login(@RequestBody loginRequest: LoginRequest): LoginResponse {
        var jwtToken = authService.login(loginRequest)
        if (jwtToken.isNullOrEmpty()) {
            return LoginResponse().apply {
                code = LoginResponse.Code.LOGIN_FAILED
                message = "This is dummy error message!"
            }
        }
        return LoginResponse().apply {
            result = true
            jwtToken = jwtToken
        }
    }

    @PostMapping("/auth/logout")
    fun logout() {
        authService.logout()
    }

    @PostMapping("/p/auth/verify_mail")
    fun verifyMail() {
        authService.verifyMail()
    }
}