package com.eco_picker.api.domain.ranking.service

import com.eco_picker.api.domain.garbage.repository.GarbageMonthlyRepository
import com.eco_picker.api.domain.ranking.data.GeneralRanking
import com.eco_picker.api.domain.ranking.data.GeneralRanker
import com.eco_picker.api.domain.ranking.data.Ranker
import com.eco_picker.api.domain.ranking.data.dto.RankerStatisticsResponse
import com.eco_picker.api.domain.user.repository.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service


@Service
class RankingService(
    private val userRepository: UserRepository,
    private val garbageMonthlyRepository: GarbageMonthlyRepository
) {
    private val logger = KotlinLogging.logger { }

    private val garbageScoreTable = mapOf(
        "plastic" to 6,
        "metal" to 5,
        "glass" to 4,
        "cardboard_paper" to 3,
        "food_scraps" to 2,
        "other" to 1
    )

    fun getRanking(offset: Int, limit: Int): GeneralRanking {
        return try {
            val allUsers = userRepository.findAll()

            val rankers = allUsers.mapNotNull { user ->
                val userId = user.id ?: return@mapNotNull null
                val monthlyData = garbageMonthlyRepository.findByUserId(userId)
                val totalScore = monthlyData.sumOf { it.totalScore }

                GeneralRanker(
                    id = userId,
                    username = user.username,
                    point = totalScore
                )
            }.sortedWith(compareByDescending<GeneralRanker> { it.point }
                .thenByDescending { it.id })

            val pagedRankers = rankers.drop(offset).take(limit) // 페이징 처리

            val currentPage = (offset / limit) + 1
            val totalItems = rankers.size.toLong()
            val totalPages = (totalItems + limit - 1) / limit

            return GeneralRanking(
                currentPage = currentPage,
                totalItems = totalItems,
                totalPages = totalPages.toInt(),
                rankers = pagedRankers
            )
        } catch (e: Exception) {
            logger.error(e) { "Failed to get ranking with offset: $offset, limit: $limit" }
            return GeneralRanking(
                currentPage = 0,
                totalItems = 0,
                totalPages = 0,
                rankers = emptyList()
            )
        }
    }

    fun getRankerDetail(userId: Long): Ranker? {
        return try {
            val userEntity = userRepository.findById(userId)
                .orElseThrow { EntityNotFoundException("User not found with id: $userId") }
            val rankerStatistics = getRankerStatistics(userId)

            Ranker(
                id = userEntity.id,
                username = userEntity.username,
                rankerStatistics = rankerStatistics
            )
        } catch (e: EntityNotFoundException) {
            logger.error(e) { "User not found with id: $userId" }
            null
        }
    }

    private fun getRankerStatistics(userId: Long): RankerStatisticsResponse.RankerStatistics {
        val monthlyData = garbageMonthlyRepository.findByUserId(userId)
        val totalCount = monthlyData.sumOf {
            it.plastic + it.metal + it.glass + it.cardboardPaper + it.foodScraps + it.other
        }

        val totalCardboardPaper = monthlyData.sumOf { it.cardboardPaper }
        val totalPlastic = monthlyData.sumOf { it.plastic }
        val totalGlass = monthlyData.sumOf { it.glass }
        val totalOther = monthlyData.sumOf { it.other }
        val totalMetal = monthlyData.sumOf { it.metal }
        val totalFoodScraps = monthlyData.sumOf { it.foodScraps }

        val cardboardPaperScore = totalCardboardPaper * garbageScoreTable["cardboard_paper"]!!
        val plasticScore = totalPlastic * garbageScoreTable["plastic"]!!
        val glassScore = totalGlass * garbageScoreTable["glass"]!!
        val otherScore = totalOther * garbageScoreTable["other"]!!
        val metalScore = totalMetal * garbageScoreTable["metal"]!!
        val foodScrapsScore = totalFoodScraps * garbageScoreTable["food_scraps"]!!

        val totalScore =
            cardboardPaperScore + plasticScore + glassScore + otherScore + metalScore + foodScrapsScore

        return RankerStatisticsResponse.RankerStatistics(
            count = RankerStatisticsResponse.Count(
                totalCount = totalCount,
                totalCardboardPaper = totalCardboardPaper,
                totalPlastic = totalPlastic,
                totalGlass = totalGlass,
                totalOther = totalOther,
                totalMetal = totalMetal,
                totalFoodScraps = totalFoodScraps
            ),
            score = RankerStatisticsResponse.Score(
                totalScore = totalScore,
                cardboardPaperScore = cardboardPaperScore,
                plasticScore = plasticScore,
                glassScore = glassScore,
                otherScore = otherScore,
                metalScore = metalScore,
                foodScrapsScore = foodScrapsScore
            )
        )
    }
}
