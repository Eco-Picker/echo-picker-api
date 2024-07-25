package com.eco_picker.api.domain.user.data.entity

import com.eco_picker.api.domain.user.constant.OnboardingStatus
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "user")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "username", nullable = false)
    val username: String,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "user_password", nullable = false)
    var password: String, // UserService 의 updatePassword 펑션에서 reassign 해야 되니까

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "onboarding_status", nullable = false)
    val onboardingStatus: OnboardingStatus,

    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: ZonedDateTime? = null// UserService 의 updatePassword 펑션에서 reassign 해야 되니까
)