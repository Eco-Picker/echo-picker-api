package com.eco_picker.api.domain.user.repository

import com.eco_picker.api.domain.user.data.entity.AuthEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.transaction.annotation.Transactional

interface AuthRepository : JpaRepository<AuthEntity, Long> {
    @Modifying
    @Transactional
    fun deleteByUserId(userId: Long): Int

    fun findByUserIdAndAccessToken(userId: Long, accessToken: String): AuthEntity?

    fun findByUserIdAndRefreshToken(userId: Long, refreshToken: String): AuthEntity?
}