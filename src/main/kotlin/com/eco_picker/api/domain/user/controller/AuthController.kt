package com.eco_picker.api.domain.user.controller

import com.eco_picker.api.domain.user.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/p/auth")
class AuthController (private val authService: AuthService){
    @PostMapping("signup")
    fun signup () {
        authService.signup()
    }

    @PostMapping("login")
    fun login () {
        authService.login()
    }

    @PostMapping("logout")
    fun logout() {
        authService.logout()
    }

    @PostMapping("verify_mail")
    fun verifyMail() {
        authService.verifyMail()
    }
}