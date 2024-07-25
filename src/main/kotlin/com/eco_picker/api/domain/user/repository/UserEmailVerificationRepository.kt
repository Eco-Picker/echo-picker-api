package com.eco_picker.api.domain.user.repository

import com.eco_picker.api.domain.user.data.entity.UserEmailVerificationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserEmailVerificationRepository : JpaRepository<UserEmailVerificationEntity, Long> {
    fun findByToken(token: String): UserEmailVerificationEntity?
}