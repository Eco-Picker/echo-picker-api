package com.eco_picker.api.domain.ranking.data.entity

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "ranking")
data class RankingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: String,

    @Column(name = "ranking_period", nullable = false)
    val rankingPeriod: String,

    @Column(name = "ranking", nullable = false)
    val ranking: Int,

    @Column(name = "score", nullable = false)
    val score: Int,

    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: ZonedDateTime? = null
)




