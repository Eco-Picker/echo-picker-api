package com.eco_picker.api.domain.newsletter.data.entity

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import jakarta.persistence.*
import java.time.ZonedDateTime


@Entity
@Table(name = "newsletter")
data class NewsletterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 100)
    val title: String,

    @Column(nullable = false, length = 1000)
    val content: String,

    @Column(nullable = false)
    val source: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val category: NewsletterCategory,

    @Column(name = "published_at", nullable = false)
    val publishedAt: ZonedDateTime,

    @Column(name = "created_at", nullable = false)
    val createdAt: ZonedDateTime = ZonedDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: ZonedDateTime? = null,
)
