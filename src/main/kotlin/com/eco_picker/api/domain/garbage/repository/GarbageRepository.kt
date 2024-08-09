package com.eco_picker.api.domain.garbage.repository

import com.eco_picker.api.domain.garbage.data.entity.GarbageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.ZonedDateTime

interface GarbageRepository : JpaRepository<GarbageEntity, Long> {

    @Query("SELECT COUNT(g) FROM GarbageEntity g WHERE g.userId = :userId AND g.collectedAt BETWEEN :startOfDay AND :endOfDay")
    fun countByUserIdAndCollectedAt(userId: Long, startOfDay: ZonedDateTime, endOfDay: ZonedDateTime): Int

    fun findByUserId(userId: Long): List<GarbageEntity?>
}
