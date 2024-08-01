package com.eco_picker.api.domain.garbage.repository

import com.eco_picker.api.domain.garbage.data.entity.GarbageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GarbageRepository : JpaRepository<GarbageEntity, Long> {

    @Query("SELECT COUNT(g) FROM GarbageEntity g WHERE g.userId = :userId AND DATE(g.collectedAt) = CURDATE()")
    fun countByUserIdAndCollectedAt(userId: Long): Int

}
