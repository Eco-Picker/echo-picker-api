package com.eco_picker.api.domain.ranking.service

import com.eco_picker.api.domain.garbage.repository.GarbageMonthlyRepository
import com.eco_picker.api.domain.garbage.data.entity.GarbageMonthlyEntity
import com.eco_picker.api.domain.ranking.data.GeneralRanking
import com.eco_picker.api.domain.ranking.data.Ranker
import com.eco_picker.api.domain.ranking.data.GeneralRanker
import com.eco_picker.api.domain.ranking.data.dto.RankerStatisticsResponse
import org.springframework.stereotype.Service
import org.springframework.data.domain.PageRequest
import com.eco_picker.api.domain.user.repository.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityNotFoundException


@Service
class RankingService (
    private val userRepository: UserRepository,
    private val garbageMonthlyRepository: GarbageMonthlyRepository
) {
    private val logger = KotlinLogging.logger { }

    private val garbageScoreTable = mapOf(
        "plastic" to 1,
        "metal" to 2,
        "glass" to 3,
        "cardboard_paper" to 4,
        "food_scraps" to 5,
        "organic_yard_waste" to 6,
        "other" to 7
    )

    fun getRanking(offset: Int, limit: Int): GeneralRanking {
        return try {
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

    fun getRankerDetail(userId: Long): Ranker {
        return try {
            val userEntity = userRepository.findById(userId).orElseThrow { EntityNotFoundException("User not found with id: $userId") }
            val rankerStatistics = getRankerStatistics(userId)

            Ranker(
                id = userEntity.id,
                username = userEntity.username,
                rankerStatistics = rankerStatistics
            )
        } catch (e: Exception) {
            logger.error(e) { "Failed to get ranker detail for userId: $userId" }
            throw e
        }
    }

    private fun getRankerStatistics(userId: Long): RankerStatisticsResponse.RankerStatistics {
        val monthlyData = garbageMonthlyRepository.findByUserId(userId)
        val totalCount = monthlyData.sumOf {
            it.plastic + it.metal + it.glass + it.cardboardPaper + it.foodScraps + it.organicYardWaste + it.other
        }

        val totalCardboardPaper = monthlyData.sumOf { it.cardboardPaper }
        val totalPlastic = monthlyData.sumOf { it.plastic }
        val totalGlass = monthlyData.sumOf { it.glass }
        val totalOther = monthlyData.sumOf { it.other }
        val totalMetal = monthlyData.sumOf { it.metal }
        val totalFoodScraps = monthlyData.sumOf { it.foodScraps }
        val totalOrganicYardWaste = monthlyData.sumOf { it.organicYardWaste }

        val cardboardPaperScore = totalCardboardPaper * garbageScoreTable["cardboard_paper"]!!
        val plasticScore = totalPlastic * garbageScoreTable["plastic"]!!
        val glassScore = totalGlass * garbageScoreTable["glass"]!!
        val otherScore = totalOther * garbageScoreTable["other"]!!
        val metalScore = totalMetal * garbageScoreTable["metal"]!!
        val foodScrapsScore = totalFoodScraps * garbageScoreTable["food_scraps"]!!
        val organicYardWasteScore = totalOrganicYardWaste * garbageScoreTable["organic_yard_waste"]!!

        val totalScore = cardboardPaperScore + plasticScore + glassScore + otherScore + metalScore + foodScrapsScore + organicYardWasteScore

        return RankerStatisticsResponse.RankerStatistics(
            count = RankerStatisticsResponse.Count(
                totalCount = totalCount,
                totalCardboardPaper = totalCardboardPaper,
                totalPlastic = totalPlastic,
                totalGlass = totalGlass,
                totalOther = totalOther,
                totalMetal = totalMetal,
                totalFoodScraps = totalFoodScraps,
                totalOrganicYardWaste = totalOrganicYardWaste
            ),
            score = RankerStatisticsResponse.Score(
                totalScore = totalScore,
                cardboardPaperScore = cardboardPaperScore,
                plasticScore = plasticScore,
                glassScore = glassScore,
                otherScore = otherScore,
                metalScore = metalScore,
                foodScrapsScore = foodScrapsScore,
                organicYardWasteScore = organicYardWasteScore
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
        val totalOrganicYardWaste = monthlyData.sumOf { it.organicYardWaste }

        return totalCardboardPaper * garbageScoreTable["cardboard_paper"]!! +
                totalPlastic * garbageScoreTable["plastic"]!! +
                totalGlass * garbageScoreTable["glass"]!! +
                totalOther * garbageScoreTable["other"]!! +
                totalMetal * garbageScoreTable["metal"]!! +
                totalFoodScraps * garbageScoreTable["food_scraps"]!! +
                totalOrganicYardWaste * garbageScoreTable["organic_yard_waste"]!!
    }
}
