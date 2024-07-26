package com.eco_picker.api.domain.user.service

import com.eco_picker.api.domain.user.data.UserInfo
import com.eco_picker.api.domain.user.data.dto.UpdatePasswordRequest
import com.eco_picker.api.domain.user.data.dto.UpdatePasswordResponse
import org.springframework.stereotype.Service
import io.github.oshai.kotlinlogging.KotlinLogging
import com.eco_picker.api.domain.user.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.ZonedDateTime

@Service
class UserService (
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    private val logger = KotlinLogging.logger { }

    // ex: user = (id = 1, username = "mihee", email = "test@test.com")
    fun getInfo(userId: Long): UserInfo {
        return try {
            // EntityNotFoundException: Thrown when the user with the userId does not exist in the user DB
            val userEntity = userRepository.findById(userId).orElseThrow { EntityNotFoundException("User not found with id: $userId") }
            // IllegalStateException: Thrown when the user entity's ID is null
            val userid = userEntity.id ?: throw IllegalStateException("User ID should not be null")
            UserInfo(
                id = userid,
                username = userEntity.username,
                email = userEntity.email,
                onboardingStatus = userEntity.onboardingStatus
            )
        // Any other exceptions
        } catch (e: Exception) {
            logger.error(e) { "Failed to get user info for userId: $userId" }
            throw e
        }
    }

    // ex: userID = 1 (jin), params - (qwe123 (curr. pw), abc123 (new pw), abc123(confirm new pw))
    fun updatePassword(userId: Long, params: UpdatePasswordRequest): UpdatePasswordResponse {
        val (password, newPassword, confirmNewPassword) = params
        val userEntity = userRepository.findById(userId).orElseThrow { EntityNotFoundException("User not found with id: $userId") }

        // Check if the current password match with the one in user DB
        if (!passwordEncoder.matches(params.password, userEntity.password)) {
            return UpdatePasswordResponse().apply {
                result = false
                code = UpdatePasswordResponse.Code.INVALID_PASSWORD
                message = "The current password is incorrect."
            }
        }
        // Check if the newly entered password is different from the previous one
        if (passwordEncoder.matches(params.newPassword, userEntity.password)) {
            return UpdatePasswordResponse().apply {
                result = false
                code = UpdatePasswordResponse.Code.NEW_PASSWORD_IS_SAME_AS_CURRENT_PASSWORD
                message = "The new password must be different from the current password."
            }
        }
        // Check if the new password and its confirmation match
        if (params.newPassword != params.confirmNewPassword) {
            return UpdatePasswordResponse().apply {
                result = false
                code = UpdatePasswordResponse.Code.NOT_MATCHED_NEW_PASSWORDS
                message = "The new password and confirmation password do not match."
            }
        }
        userEntity.password = passwordEncoder.encode(params.newPassword)
        userEntity.updatedAt = ZonedDateTime.now()
        userRepository.save(userEntity)

        return UpdatePasswordResponse().apply {
            result = true
        }
    }
}
