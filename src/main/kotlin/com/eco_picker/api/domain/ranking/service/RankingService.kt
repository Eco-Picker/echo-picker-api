package com.eco_picker.api.domain.ranking.service

import com.eco_picker.api.domain.garbage.data.entity.GarbageMonthlyEntity
import com.eco_picker.api.domain.garbage.repository.GarbageMonthlyRepository
import com.eco_picker.api.domain.ranking.data.GeneralRanker
import com.eco_picker.api.domain.ranking.data.GeneralRanking
import com.eco_picker.api.domain.ranking.data.Ranker
import com.eco_picker.api.domain.ranking.data.dto.RankerStatisticsResponse
import com.eco_picker.api.domain.user.repository.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.PageRequest
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
            // @todo pageable 로직 변경되었으니 추후 getNewsletterSummaries 쪽 소스 참조해주세요.
            val pageable = PageRequest.of(offset, limit)
            val users = userRepository.findAll(pageable)

            val rankers = users.mapNotNull { user ->
                val userId = user.id ?: return@mapNotNull null // Skip users with null IDs
                val monthlyData = garbageMonthlyRepository.findByUserId(userId)
                val totalScore = calculateTotalScore(monthlyData)

                GeneralRanker(
                    id = userId,
                    username = user.username,
                    point = totalScore
                )
            }.sortedByDescending { it.point }

            GeneralRanking(rankers = rankers)
        } catch (e: Exception) {
            logger.error(e) { "Failed to get ranking with offset: $offset, limit: $limit" }
            throw e
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

    private fun calculateTotalScore(monthlyData: List<GarbageMonthlyEntity>): Int {
        val totalCardboardPaper = monthlyData.sumOf { it.cardboardPaper }
        val totalPlastic = monthlyData.sumOf { it.plastic }
        val totalGlass = monthlyData.sumOf { it.glass }
        val totalOther = monthlyData.sumOf { it.other }
        val totalMetal = monthlyData.sumOf { it.metal }
        val totalFoodScraps = monthlyData.sumOf { it.foodScraps }

        return totalCardboardPaper * garbageScoreTable["cardboard_paper"]!! +
                totalPlastic * garbageScoreTable["plastic"]!! +
                totalGlass * garbageScoreTable["glass"]!! +
                totalOther * garbageScoreTable["other"]!! +
                totalMetal * garbageScoreTable["metal"]!! +
                totalFoodScraps * garbageScoreTable["food_scraps"]!!
    }
}
