package com.eco_picker.api.domain.user.controller

import com.eco_picker.api.domain.user.data.dto.UpdatePasswordRequest
import com.eco_picker.api.domain.user.data.dto.UpdatePasswordResponse
import com.eco_picker.api.domain.user.data.dto.UserInfoResponse
import com.eco_picker.api.domain.user.data.dto.UserStatisticsResponse
import com.eco_picker.api.domain.user.service.UserService
import com.eco_picker.api.global.data.UserPrincipal
import com.eco_picker.api.global.document.OpenAPIConfig.Companion.JWT
import com.eco_picker.api.global.document.OperationTag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {
    @Operation(
        tags = [OperationTag.USER],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get a user information",
    )
    @GetMapping("info")
    fun getInfo(@AuthenticationPrincipal principal: UserPrincipal): UserInfoResponse {
        val userInfo = userService.getInfo(userId = principal.id)
        return UserInfoResponse().apply {
            result = true
            this.userInfo = userInfo
        }
    }

    @Operation(
        tags = [OperationTag.USER],
        security = [SecurityRequirement(name = JWT)],
        summary = "Update a user password",
    )
    @PostMapping("update_password")
    fun updatePassword(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody updatePasswordRequest: UpdatePasswordRequest
    ): UpdatePasswordResponse {
        return userService.updatePassword(userId = principal.id, params = updatePasswordRequest)
    }

    @Operation(
        tags = [OperationTag.USER],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get user statistics",
    )
    @GetMapping("statistics")
    fun getStatistics(@AuthenticationPrincipal principal: UserPrincipal): UserStatisticsResponse {
        return userService.getStatistics(userId = principal.id)
    }
}