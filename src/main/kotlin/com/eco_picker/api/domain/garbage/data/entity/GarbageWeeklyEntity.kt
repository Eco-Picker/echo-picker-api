package com.eco_picker.api.domain.garbage.data.entity

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "garbage_weekly")
data class GarbageWeeklyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "collected_week", nullable = false)
    val collectedWeek: Int,

    @Column(name = "plastic")
    val plastic: Int = 0,

    @Column(name = "metal")
    val metal: Int = 0,

    @Column(name = "glass")
    val glass: Int = 0,

    @Column(name = "cardboard_paper")
    val cardboardPaper: Int = 0,

    @Column(name = "food_scraps")
    val foodScraps: Int = 0,

    @Column(name = "organic_yard_waste")
    val organicYardWaste: Int = 0,

    @Column(name = "other")
    val other: Int = 0,

    @Column(name = "total_score", nullable = false)
    val totalScore: Int = 0,

    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: ZonedDateTime? = null
)
