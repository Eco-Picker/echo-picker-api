package com.eco_picker.api.domain.user.data.entity

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "user_email_verification")
data class UserEmailVerificationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "token", nullable = false)
    val token: String,

    @Column(name = "issued_at", nullable = false)
    val issuedAt: ZonedDateTime,

    @Column(name = "verified_at")
    var verifiedAt: ZonedDateTime? = null,
)
