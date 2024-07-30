package com.eco_picker.api.domain.newsletter.repository

import com.eco_picker.api.domain.newsletter.data.entity.EventEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<EventEntity, Long> {}