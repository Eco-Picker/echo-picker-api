package com.eco_picker.api.domain.garbage.data.entity

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "garbage_monthly")
data class GarbageMonthlyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "collected_month", nullable = false)
    val collectedMonth: String,

    @Column(name = "plastic")
    var plastic: Int = 0,

    @Column(name = "metal")
    var metal: Int = 0,

    @Column(name = "glass")
    var glass: Int = 0,

    @Column(name = "cardboard_paper")
    var cardboardPaper: Int = 0,

    @Column(name = "food_scraps")
    var foodScraps: Int = 0,

    @Column(name = "other")
    var other: Int = 0,

    @Column(name = "total_score", nullable = false)
    var totalScore: Int = 0,

    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: ZonedDateTime? = null
)

