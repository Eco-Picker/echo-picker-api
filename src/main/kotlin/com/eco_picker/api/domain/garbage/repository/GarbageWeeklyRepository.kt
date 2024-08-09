package com.eco_picker.api.domain.garbage.repository

import com.eco_picker.api.domain.garbage.data.entity.GarbageWeeklyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface GarbageWeeklyRepository : JpaRepository<GarbageWeeklyEntity, Long> {
    fun findByUserIdAndCollectedWeek(userId: Long, collectedWeek: Int): List<GarbageWeeklyEntity>

    @Query("SELECT g FROM GarbageWeeklyEntity g WHERE g.userId = :userId AND g.collectedWeek = :collectedWeek AND g.collectedYear = :collectedYear")
    fun findOneByUserIdAndCollectedWeekAndCollectedYear(userId: Long, collectedWeek: Int, collectedYear: Int): GarbageWeeklyEntity?
}
