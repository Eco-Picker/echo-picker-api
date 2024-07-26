package com.eco_picker.api.domain.ranking.service

import com.eco_picker.api.domain.ranking.constant.RankingPeriod
import com.eco_picker.api.domain.ranking.data.Ranker
import com.eco_picker.api.domain.ranking.data.Ranking
import com.eco_picker.api.domain.ranking.repository.RankingRepository
import org.springframework.stereotype.Service
import org.springframework.data.domain.PageRequest
import com.eco_picker.api.domain.user.repository.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityNotFoundException

@Service
class RankingService (
    private val rankingRepository: RankingRepository,
    private val userRepository: UserRepository
) {
    private val logger = KotlinLogging.logger { }

    fun getDailyRanking(userId: Long, offset: Int, limit: Int): Ranking {
        return this.getRanking(userId, RankingPeriod.DAILY, offset, limit)
    }

    fun getWeeklyRanking(userId: Long, offset: Int, limit: Int): Ranking {
        return this.getRanking(userId, RankingPeriod.WEEKLY, offset, limit)
    }

    fun getMonthlyRanking(userId: Long, offset: Int, limit: Int): Ranking {
        return this.getRanking(userId, RankingPeriod.MONTHLY, offset, limit)
    }

    fun getRankerDetail(rankingId: Long): Ranker {
        return try {
            val rankingEntity = rankingRepository.findById(rankingId).orElseThrow { EntityNotFoundException("Ranker not found with rankingId: $rankingId") }
            val userEntity = userRepository.findById(rankingEntity.userId.toLong()).orElseThrow { EntityNotFoundException("User not found with id: ${rankingEntity.userId}") }
            Ranker(id = rankingEntity.userId.toLong(), username = userEntity.username, point = rankingEntity.score)
        } catch (e: Exception) {
            logger.error(e) { "Failed to get ranker detail for rankingId: $rankingId" }
            throw e
        }
    }

    private fun getRanking(userId: Long, period: RankingPeriod, offset: Int, limit: Int): Ranking {
        return try {
            val periodString = period.name.lowercase()
            val pageable = PageRequest.of(offset, limit)
            val results = rankingRepository.findTopRankersByPeriod(periodString, pageable)

            val rankers = results.map {
                val userEntity = userRepository.findById(it.userId.toLong()).orElseThrow { EntityNotFoundException("User not found with id: ${it.userId}") }
                Ranker(id = it.userId.toLong(), username = userEntity.username, point = it.score)
            }
            Ranking(rankers = rankers)
        } catch (e: Exception) {
            logger.error(e) { "Failed to get ranking for period: $period, userId: $userId, offset: $offset, limit: $limit" }
            throw e
        }
    }
}

//        daily data ex:
//        Ranker(id = 1L, username = "Alice", point = 1200),
//        Ranker(id = 2L, username = "Bob", point = 1150),
//        Ranker(id = 3L, username = "Charlie", point = 1100),
//        Ranker(id = 4L, username = "David", point = 1000),
//        Ranker(id = 5L, username = "Eve", point = 950)
//
//        weekly data ex:
//        Ranker(id = 1L, username = "Alice", point = 7000),
//        Ranker(id = 2L, username = "Bob", point = 6800),
//        Ranker(id = 3L, username = "Charlie", point = 6700),
//        Ranker(id = 4L, username = "David", point = 6600),
//        Ranker(id = 5L, username = "Eve", point = 6500)
//
//        monthly data ex:
//        rankers = listOf(
//            Ranker(id = 1L, username = "Alice", point = 30000),
//            Ranker(id = 2L, username = "Bob", point = 29000),
//            Ranker(id = 3L, username = "Charlie", point = 28000),
//            Ranker(id = 4L, username = "David", point = 27000),
//            Ranker(id = 5L, username = "Eve", point = 26000)
