package com.eco_picker.api.domain.user.data.entity

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "auth")
data class AuthEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "access_token", nullable = false)
    val accessToken: String,

    @Column(name = "expires_at", nullable = false)
    val expiresAt: ZonedDateTime,

    @Column(name = "refresh_token", nullable = false)
    val refreshToken: String,

    @Column(name = "refresh_expires_at", nullable = false)
    val refreshExpiresAt: ZonedDateTime,

    @Column(name = "updated_at")
    val updatedAt: ZonedDateTime? = null,

    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(),
)
