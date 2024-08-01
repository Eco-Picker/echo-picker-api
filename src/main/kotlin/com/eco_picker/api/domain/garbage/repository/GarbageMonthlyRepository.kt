package com.eco_picker.api.domain.garbage.repository

import com.eco_picker.api.domain.garbage.data.entity.GarbageMonthlyEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GarbageMonthlyRepository : JpaRepository<GarbageMonthlyEntity, Long> {

    fun findByUserId(userId: Long): List<GarbageMonthlyEntity>

    fun findByUserIdAndCollectedMonth(userId: Long, collectedMonth: String): GarbageMonthlyEntity?
}
