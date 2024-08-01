package com.eco_picker.api.domain.garbage.repository

import com.eco_picker.api.domain.garbage.data.entity.GarbageWeeklyEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GarbageWeeklyRepository : JpaRepository<GarbageWeeklyEntity, Long> {
    fun findByUserIdAndCollectedWeek(userId: Long, collectedWeek: Int): List<GarbageWeeklyEntity>
}
