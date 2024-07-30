package com.eco_picker.api.domain.newsletter.data.entity

import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "newsletter_education")
data class EducationalContentEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val content: String,

    @Column(nullable = false)
    val source: String,

    @Column(name = "published_at", nullable = false)
    val publishedAt: ZonedDateTime,

    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: ZonedDateTime? = null,
)
