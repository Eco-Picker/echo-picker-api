package com.eco_picker.api.domain.user.controller

import com.eco_picker.api.domain.user.data.dto.*
import com.eco_picker.api.domain.user.service.AuthService
import com.eco_picker.api.global.data.DefaultResponse
import com.eco_picker.api.global.data.UserPrincipal
import com.eco_picker.api.global.document.OpenAPIConfig.Companion.JWT
import com.eco_picker.api.global.document.OperationTag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView

@RestController
class AuthController(private val authService: AuthService) {
    @Operation(
        tags = [OperationTag.AUTHENTICATION],
        summary = "Signup",
    )
    @PostMapping("/p/auth/signup")
    fun signup(@Valid @RequestBody signupRequest: SignupRequest): SignupResponse {
        return authService.signup(signupRequest)
    }

    @Operation(
        tags = [OperationTag.AUTHENTICATION],
        summary = "Login",
    )
    @PostMapping("/p/auth/login")
    fun login(@Valid @RequestBody loginRequest: LoginRequest): LoginResponse {
        return authService.login(loginRequest)
    }

    @Operation(
        tags = [OperationTag.AUTHENTICATION],
        security = [SecurityRequirement(name = JWT)],
        summary = "Renew a access token",
    )
    @PostMapping("/auth/renew_access_token")
    fun renewAccessToken(
        @AuthenticationPrincipal principal: UserPrincipal,
        @Valid @RequestBody renewAccessTokenRequest: RenewAccessTokenRequest
    ): RenewAccessTokenResponse {
        return this.authService.renewAccessToken(
            userId = principal.id,
            refreshToken = renewAccessTokenRequest.refreshToken
        )
    }

    @Operation(
        tags = [OperationTag.AUTHENTICATION],
        security = [SecurityRequirement(name = JWT)],
        summary = "Logout",
    )
    @PostMapping("/auth/logout")
    fun logout(@AuthenticationPrincipal principal: UserPrincipal): DefaultResponse {
        val result = authService.logout(principal.id)
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
        @Schema(
            description = "- Encrypted token generated for sending signup verification email.\n" +
                    "- This response from this API is not an HTTP response but a an HTML redirect."
        )
        @PathVariable token: String,
        redirectAttributes: RedirectAttributes
    ): RedirectView {
        val response = authService.verifyMail(token)
        if (!response.result) {
            redirectAttributes.addFlashAttribute("error", response.code)
            return RedirectView("/email-verification-failed.html")
        } else {
            return RedirectView("/email-verification-success.html")
        }
    }

    @Operation(
        tags = [OperationTag.AUTHENTICATION],
        summary = "Send a temp password"
    )
    @PostMapping("/p/auth/send_temp_password")
    fun sendTempPasswordMail(@Valid @RequestBody sendTempPasswordRequest: SendTempPasswordRequest): DefaultResponse {
        val result = this.authService.sendTempPassword(email = sendTempPasswordRequest.email)
        return DefaultResponse().apply {
            this.result = result
        }
    }
}