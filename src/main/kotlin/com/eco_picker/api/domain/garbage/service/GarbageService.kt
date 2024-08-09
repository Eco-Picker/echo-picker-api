package com.eco_picker.api.domain.garbage.service

import com.eco_picker.api.domain.garbage.constant.GarbageCategory
import com.eco_picker.api.domain.garbage.data.Garbage
import com.eco_picker.api.domain.garbage.data.SaveGarbage
import com.eco_picker.api.domain.garbage.data.GeminiGarbage
import com.eco_picker.api.domain.garbage.repository.GarbageRepository
import com.eco_picker.api.domain.garbage.repository.GarbageWeeklyRepository
import com.eco_picker.api.domain.garbage.repository.GarbageMonthlyRepository
import com.eco_picker.api.domain.garbage.data.entity.GarbageEntity
import com.eco_picker.api.domain.garbage.data.entity.GarbageWeeklyEntity
import com.eco_picker.api.domain.garbage.data.entity.GarbageMonthlyEntity
import com.eco_picker.api.global.support.FileManager
import com.eco_picker.api.global.support.GeminiManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import net.coobird.thumbnailator.Thumbnails
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.IsoFields
import java.util.Base64

@Service
class GarbageService(
    private val fileManager: FileManager,
    private val geminiManager: GeminiManager,
    private val garbageRepository: GarbageRepository,
    private val garbageWeeklyRepository: GarbageWeeklyRepository,
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

    fun analyzeImage(userId: Long, file: MultipartFile): GeminiGarbage? {
        logger.debug { "FILE FROM FRONTEND: '${file}'" }
        // Resize the image
        val resizedImage = resizeImage(file)

        // Convert resized image to base64
        val base64Image = Base64.getEncoder().encodeToString(resizedImage)

        // Use GeminiManager to analyze the image
        val geminiResult = geminiManager.analyzeImage(base64Image)

        return geminiResult?.let {
            // Log the category value for debugging
            logger.debug { "Gemini category returned: '${it.category}'" }

            // Convert the category string to enum safely
            val category = convertToGarbageCategory(it.category)

            if (category != null) {
                GeminiGarbage(
                    name = it.name,
                    category = category,
                    pickedUpAt = ZonedDateTime.now()
                )
            } else {
                logger.error { "Failed to map category for garbage name: '${it.name}' with category: '${it.category}'" }
                null // Handle null case appropriately
            }
        }
    }

    private fun convertToGarbageCategory(category: String): GarbageCategory? {
        return try {
            GarbageCategory.valueOf(category.trim().uppercase())
        } catch (e: IllegalArgumentException) {
            // Log the error and the invalid category string
            logger.error { "Invalid garbage category returned by Gemini: $category" }
            null
        }
    }

    private fun resizeImage(file: MultipartFile): ByteArray {
        val originalImage = file.inputStream
        val outputStream = ByteArrayOutputStream()
        Thumbnails.of(originalImage)
            .size(512, 512)
            .outputFormat("jpg")
            .toOutputStream(outputStream)
        return outputStream.toByteArray()
    }

    fun getGarbage(userId: Long, id: Long): Garbage? {
        val garbageEntity = this.garbageRepository.findById(id)
        if (garbageEntity.isPresent) {
            val entity = garbageEntity.get()
            // Ensure the garbage belongs to the requesting user
            if (entity.userId == userId) {
                return Garbage(
                    id = entity.id!!,
                    userId = entity.userId,
                    name = entity.name,
                    pickedUpAt = entity.collectedAt,
                    latitude = entity.latitude,
                    longitude = entity.longitude,
                    category = entity.category
                )
            }
        }
        return null
    }

    @Transactional
    fun saveGarbage(garbage: SaveGarbage, userId: Long): Boolean {
        try {
            logger.debug { "_________-------------------_________________Saving garbage with name: ${garbage.name}" }

            val garbageEntity = GarbageEntity(
                userId = userId,
                name = garbage.name,
                category = garbage.category,
                latitude = garbage.latitude,
                longitude = garbage.longitude,
                collectedAt = garbage.pickedUpAt,
                createdAt = ZonedDateTime.now(),
                updatedAt = ZonedDateTime.now()
            )

            logger.debug { "CHECKCHECKCHECK_________-------------------_________________${garbageEntity.userId} ${garbageEntity.name} ${garbageEntity.category}" }
            garbageRepository.save(garbageEntity)

            updateGarbageWeekly(garbageEntity)
            updateGarbageMonthly(garbageEntity)

            return true
        } catch (e: Exception) {
            logger.error(e) { "Failed to save garbage data" }
            return false
        }
    }

    private fun updateGarbageWeekly(garbageEntity: GarbageEntity) {
        val weekOfYear = garbageEntity.collectedAt.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
        val year = garbageEntity.collectedAt.year
        val weeklyEntity = garbageWeeklyRepository.findOneByUserIdAndCollectedWeekAndCollectedYear(
            garbageEntity.userId,
            weekOfYear,
            year
        ) ?: GarbageWeeklyEntity(
            userId = garbageEntity.userId,
            collectedWeek = weekOfYear,
            collectedYear = year,
            createdAt = ZonedDateTime.now()
        )
        incrementCategoryCount(weeklyEntity, garbageEntity.category)
        val scoreToAdd = garbageScoreTable[garbageEntity.category.name.lowercase()] ?: 0
        weeklyEntity.totalScore += scoreToAdd
        weeklyEntity.updatedAt = ZonedDateTime.now()
        garbageWeeklyRepository.save(weeklyEntity)
    }

    private fun updateGarbageMonthly(garbageEntity: GarbageEntity) {
        val month = garbageEntity.collectedAt.format(DateTimeFormatter.ofPattern("yyyy-MM"))
        val monthlyEntity = garbageMonthlyRepository.findByUserIdAndCollectedMonth(garbageEntity.userId, month)
            ?: GarbageMonthlyEntity(
                userId = garbageEntity.userId,
                collectedMonth = month,
                createdAt = ZonedDateTime.now()
            )
        incrementCategoryCount(monthlyEntity, garbageEntity.category)
        val scoreToAdd = garbageScoreTable[garbageEntity.category.name.lowercase()] ?: 0
        monthlyEntity.totalScore += scoreToAdd
        monthlyEntity.updatedAt = ZonedDateTime.now()
        garbageMonthlyRepository.save(monthlyEntity)
    }

    private fun incrementCategoryCount(entity: Any, category: GarbageCategory) {
        when (entity) {
            is GarbageWeeklyEntity -> when (category) {
                GarbageCategory.PLASTIC -> entity.plastic += 1
                GarbageCategory.METAL -> entity.metal += 1
                GarbageCategory.GLASS -> entity.glass += 1
                GarbageCategory.CARDBOARD_PAPER -> entity.cardboardPaper += 1
                GarbageCategory.FOOD_SCRAPS -> entity.foodScraps += 1
                GarbageCategory.OTHER -> entity.other += 1
            }
            is GarbageMonthlyEntity -> when (category) {
                GarbageCategory.PLASTIC -> entity.plastic += 1
                GarbageCategory.METAL -> entity.metal += 1
                GarbageCategory.GLASS -> entity.glass += 1
                GarbageCategory.CARDBOARD_PAPER -> entity.cardboardPaper += 1
                GarbageCategory.FOOD_SCRAPS -> entity.foodScraps += 1
                GarbageCategory.OTHER -> entity.other += 1
            }
        }
    }
}
