package com.eco_picker.api.domain.user.service

import com.eco_picker.api.domain.user.data.UserInfo
import com.eco_picker.api.domain.user.data.dto.UpdatePasswordRequest
import com.eco_picker.api.domain.user.data.dto.UpdatePasswordResponse
import com.eco_picker.api.domain.user.data.dto.UserStatisticsResponse
import com.eco_picker.api.domain.user.repository.UserRepository
import com.eco_picker.api.domain.garbage.repository.GarbageMonthlyRepository
import com.eco_picker.api.domain.garbage.repository.GarbageWeeklyRepository
import com.eco_picker.api.domain.garbage.repository.GarbageRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val garbageMonthlyRepository: GarbageMonthlyRepository,
    private val garbageWeeklyRepository: GarbageWeeklyRepository,
    private val garbageRepository: GarbageRepository,
    private val passwordEncoder: PasswordEncoder
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

    fun getInfo(userId: Long): UserInfo {
        return try {
            val userEntity = userRepository.findById(userId).orElseThrow { EntityNotFoundException("User not found with id: $userId") }
            val userid = userEntity.id ?: throw IllegalStateException("User ID should not be null")
            UserInfo(
                id = userid,
                username = userEntity.username,
                email = userEntity.email,
                onboardingStatus = userEntity.onboardingStatus
            )
        } catch (e: EntityNotFoundException) {
            logger.error(e) { "User not found with id: $userId" }
            throw e
        } catch (e: IllegalStateException) {
            logger.error(e) { "User ID should not be null for userId: $userId" }
            throw e
        } catch (e: Exception) {
            logger.error(e) { "Failed to get user info for userId: $userId" }
            throw e
        }
    }

    fun updatePassword(userId: Long, params: UpdatePasswordRequest): UpdatePasswordResponse {
        val response = UpdatePasswordResponse()
        return try {
            val (password, newPassword, confirmNewPassword) = params
            val userEntity = userRepository.findById(userId).orElseThrow { EntityNotFoundException("User not found with id: $userId") }

            if (!passwordEncoder.matches(password, userEntity.password)) {
                return response.apply {
                    result = false
                    code = UpdatePasswordResponse.Code.INVALID_PASSWORD
                    message = "The current password is incorrect."
                }
            }

            if (passwordEncoder.matches(newPassword, userEntity.password)) {
                return response.apply {
                    result = false
                    code = UpdatePasswordResponse.Code.NEW_PASSWORD_IS_SAME_AS_CURRENT_PASSWORD
                    message = "The new password must be different from the current password."
                }
            }

            if (newPassword != confirmNewPassword) {
                return response.apply {
                    result = false
                    code = UpdatePasswordResponse.Code.NOT_MATCHED_NEW_PASSWORDS
                    message = "The new password and confirmation password do not match."
                }
            }

            userEntity.password = passwordEncoder.encode(newPassword)
            userEntity.updatedAt = ZonedDateTime.now()
            userRepository.save(userEntity)

            response.apply {
                result = true
            }
        } catch (e: EntityNotFoundException) {
            logger.error(e) { "User not found with id: $userId" }
            response.apply {
                result = false
                code = UpdatePasswordResponse.Code.USER_NOT_FOUND
                message = "User not found."
            }
        } catch (e: Exception) {
            logger.error(e) { "Failed to update password for userId: $userId" }
            response.apply {
                result = false
                code = UpdatePasswordResponse.Code.INTERNAL_SERVER_ERROR
                message = "An error occurred while updating the password."
            }
        }
    }

    fun getStatistics(userId: Long): UserStatisticsResponse {
        val response = UserStatisticsResponse()

        return try {
            // Check if the user exists before proceeding with statistics
            val userExists = userRepository.existsById(userId)
            if (!userExists) {
                throw EntityNotFoundException("User not found with id: $userId")
            }

            val now = ZonedDateTime.now()
            val currentMonth = now.format(DateTimeFormatter.ofPattern("yyyyMM"))
            val currentWeek = now.get(WeekFields.of(Locale.getDefault()).weekOfYear())

            // Total count from garbage_monthly
            val monthlyData = garbageMonthlyRepository.findByUserId(userId)
            val totalCount = monthlyData.sumOf {
                it.plastic + it.metal + it.glass + it.cardboardPaper + it.foodScraps + it.organicYardWaste + it.other
            }

            // Total daily count from garbage
            val totalDailyCount = garbageRepository.countByUserIdAndCollectedAt(userId)

            // Total weekly count from garbage_weekly
            val weeklyData = garbageWeeklyRepository.findByUserIdAndCollectedWeek(userId, currentWeek)
            val totalWeeklyCount = weeklyData.sumOf {
                it.plastic + it.metal + it.glass + it.cardboardPaper + it.foodScraps + it.organicYardWaste + it.other
            }

            // Total monthly count from garbage_monthly for current month
            val currentMonthlyData = garbageMonthlyRepository.findByUserIdAndCollectedMonth(userId, currentMonth)
            val totalMonthlyCount = currentMonthlyData?.let {
                it.plastic + it.metal + it.glass + it.cardboardPaper + it.foodScraps + it.organicYardWaste + it.other
            } ?: 0

            // Calculate totals for each garbage category
            val totalCardboardPaper = monthlyData.sumOf { it.cardboardPaper }
            val totalPlastic = monthlyData.sumOf { it.plastic }
            val totalGlass = monthlyData.sumOf { it.glass }
            val totalOther = monthlyData.sumOf { it.other }
            val totalMetal = monthlyData.sumOf { it.metal }
            val totalFoodScraps = monthlyData.sumOf { it.foodScraps }
            val totalOrganicYardWaste = monthlyData.sumOf { it.organicYardWaste }

            // Calculate scores for each garbage category
            val cardboardPaperScore = totalCardboardPaper * garbageScoreTable["cardboard_paper"]!!
            val plasticScore = totalPlastic * garbageScoreTable["plastic"]!!
            val glassScore = totalGlass * garbageScoreTable["glass"]!!
            val otherScore = totalOther * garbageScoreTable["other"]!!
            val metalScore = totalMetal * garbageScoreTable["metal"]!!
            val foodScrapsScore = totalFoodScraps * garbageScoreTable["food_scraps"]!!
            val organicYardWasteScore = totalOrganicYardWaste * garbageScoreTable["organic_yard_waste"]!!

            // Calculate the total score
            val totalScore = cardboardPaperScore + plasticScore + glassScore + otherScore + metalScore + foodScrapsScore + organicYardWasteScore

            response.userStatistics = UserStatisticsResponse.UserStatistics(
                count = UserStatisticsResponse.Count(
                    totalCount = totalCount,
                    totalDailyCount = totalDailyCount,
                    totalWeeklyCount = totalWeeklyCount,
                    totalMonthlyCount = totalMonthlyCount,
                    totalCardboardPaper = totalCardboardPaper,
                    totalPlastic = totalPlastic,
                    totalGlass = totalGlass,
                    totalOther = totalOther,
                    totalMetal = totalMetal,
                    totalFoodScraps = totalFoodScraps,
                    totalOrganicYardWaste = totalOrganicYardWaste
                ),
                score = UserStatisticsResponse.Score(
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
            response.result = true
            response
        } catch (e: EntityNotFoundException) {
            logger.error(e) { "User not found with id: $userId" }
            response.apply {
                result = false
                code = UserStatisticsResponse.Code.USER_NOT_FOUND
            }
        } catch (e: Exception) {
            logger.error(e) { "Failed to get statistics for userId: $userId" }
            response.apply {
                result = false
                code = UserStatisticsResponse.Code.INTERNAL_SERVER_ERROR
            }
        }
    }
}
