package com.eco_picker.api.domain.user.controller

import com.eco_picker.api.domain.user.data.dto.*
import com.eco_picker.api.domain.user.service.AuthService
import com.eco_picker.api.global.data.DefaultResponse
import com.eco_picker.api.global.document.OperationTag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.*

@RestController
class AuthController(private val authService: AuthService) {
    @Operation(
        tags = [OperationTag.AUTHENTICATION],
        summary = "Signup",
    )
    @PostMapping("/p/auth/signup")
    fun signup(@RequestBody signupRequest: SignupRequest): SignupResponse {
        return authService.signup(signupRequest)
    }

    @Operation(
        tags = [OperationTag.AUTHENTICATION],
        summary = "Login",
    )
    @PostMapping("/p/auth/login")
    fun login(@RequestBody loginRequest: LoginRequest): LoginResponse {
        return authService.login(loginRequest)
    }

    @Operation(
        tags = [OperationTag.AUTHENTICATION],
        summary = "Renew a access token",
    )
    @PostMapping("/auth/renew_access_token")
    fun renewAccessToken(@RequestBody renewAccessTokenRequest: RenewAccessTokenRequest): RenewAccessTokenResponse {
        val accessToken = this.authService.renewAccessToken(renewAccessTokenRequest.refreshToken)
        accessToken?.let {
            return RenewAccessTokenResponse(accessToken).apply {
                result = true
            }
        }
        return RenewAccessTokenResponse()
    }


    @Operation(
        tags = [OperationTag.AUTHENTICATION],
        summary = "Logout",
    )
    @PostMapping("/auth/logout")
    fun logout(): DefaultResponse {
        val result = authService.logout()
        return DefaultResponse().apply {
            this.result = result
        }
    }

    @Operation(
        tags = [OperationTag.AUTHENTICATION],
        summary = "Verify a mail",
    )
    @GetMapping("/p/auth/verify_mail/{token}")
    fun verifyMail(
        @Schema(description = "Encrypted token generated for sending signup verification email.")
        @PathVariable token: String
    ): VerifyMailResponse {
        return authService.verifyMail(token)
    }

    @Operation(
        tags = [OperationTag.AUTHENTICATION],
        summary = "Send a temp password"
    )
    @PostMapping("/p/auth/send_temp_password")
    fun sendTempPasswordMail(@RequestBody sendTempPasswordRequest: SendTempPasswordRequest): DefaultResponse {
        val result = this.authService.sendTempPassword()
        return DefaultResponse().apply {
            this.result = result
        }
    }
}