package com.eco_picker.api.domain.ranking.repository

import com.eco_picker.api.domain.ranking.data.entity.RankingEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface RankingRepository : JpaRepository<RankingEntity, Long> {

    @Query("SELECT r FROM RankingEntity r WHERE r.rankingPeriod = :period ORDER BY r.score DESC")
    fun findTopRankersByPeriod(
        @Param("period") period: String,
        pageable: org.springframework.data.domain.Pageable
    ): List<RankingEntity>
}
