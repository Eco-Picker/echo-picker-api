package com.eco_picker.api.domain.user.controller

import com.eco_picker.api.domain.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController (private val userService: UserService) {
    @GetMapping("info")
    fun getInfo () {
        userService.getInfo()
    }

    @PostMapping("update_password")
    fun updatePassword () {
        userService.updatePassword()
    }
}