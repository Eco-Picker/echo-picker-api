package com.eco_picker.api.domain.newsletter.repository

import com.eco_picker.api.domain.newsletter.data.entity.EducationalContentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EducationalContentRepository : JpaRepository<EducationalContentEntity, Long> {}
