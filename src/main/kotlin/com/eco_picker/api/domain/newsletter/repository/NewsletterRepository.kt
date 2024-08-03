package com.eco_picker.api.domain.newsletter.repository

import com.eco_picker.api.domain.newsletter.constant.NewsletterCategory
import com.eco_picker.api.domain.newsletter.data.entity.NewsletterEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface NewsletterRepository : JpaRepository<NewsletterEntity, Long> {
    @Query("SELECT n FROM NewsletterEntity n WHERE n.category = :category")
    fun findAllByCategory(@Param("category") category: NewsletterCategory, pageable: Pageable): Page<NewsletterEntity>

    @Query("SELECT n FROM NewsletterEntity n ORDER BY RAND() LIMIT 1")
    fun findOneRandom(): NewsletterEntity?
}