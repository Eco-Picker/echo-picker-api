package com.eco_picker.api.domain.user.controller

import com.eco_picker.api.domain.user.data.dto.UpdatePasswordRequest
import com.eco_picker.api.domain.user.data.dto.UpdatePasswordResponse
import com.eco_picker.api.domain.user.data.dto.UserInfoResponse
import com.eco_picker.api.domain.user.service.UserService
import com.eco_picker.api.global.document.OperationTag
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {
    @Operation(
        tags = [OperationTag.USER],
        summary = "Get a user information",
    )
    @GetMapping("info")
    fun getInfo(): UserInfoResponse {
        val userInfo = userService.getInfo()
        return UserInfoResponse().apply {
            result = true
            this.userInfo = userInfo
        }
    }

    @Operation(
        tags = [OperationTag.USER],
        summary = "Update a user password",
    )
    @PostMapping("update_password")
    fun updatePassword(@RequestBody updatePasswordRequest: UpdatePasswordRequest): UpdatePasswordResponse {
        return userService.updatePassword(updatePasswordRequest)
    }
}