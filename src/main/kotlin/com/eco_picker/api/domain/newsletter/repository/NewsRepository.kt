package com.eco_picker.api.domain.newsletter.repository

import com.eco_picker.api.domain.newsletter.data.entity.NewsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface NewsRepository : JpaRepository<NewsEntity, Long> {}