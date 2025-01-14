package com.eco_picker.api.domain.garbage.data.entity

import com.eco_picker.api.domain.garbage.constant.GarbageCategory
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "garbage")
data class GarbageEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "garbage_name", length = 255, nullable = false)
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    val category: GarbageCategory,

    @Column(name = "latitude", nullable = false)
    val latitude: Double,

    @Column(name = "longitude", nullable = false)
    val longitude: Double,

    @Column(name = "collected_at", nullable = false)
    val collectedAt: ZonedDateTime,

    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime,

    @Column(name = "updated_at")
    var updatedAt: ZonedDateTime? = null
)
