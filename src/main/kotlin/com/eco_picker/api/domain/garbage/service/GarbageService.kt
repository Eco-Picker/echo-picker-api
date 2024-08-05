package com.eco_picker.api.domain.garbage.service

import com.eco_picker.api.domain.garbage.constant.GarbageCategory
import com.eco_picker.api.domain.garbage.data.Garbage
import com.eco_picker.api.domain.garbage.repository.GarbageRepository
import com.eco_picker.api.global.support.FileManager
import com.eco_picker.api.global.support.GeminiManager
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class GarbageService(
    private val fileManager: FileManager,
    private val geminiManager: GeminiManager,
    private val garbageRepository: GarbageRepository
) {
    // for fake data - just for this weekend
    fun analyzeImage(userId: Long, category: GarbageCategory, imageData: String): Garbage? {
        // Fake analysis using imageData string
        return Garbage(
            id = 0L,
            name = "string",
            category = GarbageCategory.PLASTIC,
            memo = "string",
            pickedUpAt = ZonedDateTime.parse("2024-08-03T02:56:16.914Z"),
            latitude = 37.7749, // Example latitude
            longitude = -122.4194 // Example longitude
        )
    }

//    fun analyzeImage(userId: Long, category: GarbageCategory, file: MultipartFile): Garbage? {
//        // @todo 업로드 이미지
//        val uploadedFile = this.fileManager.saveFile(file)
//
//        uploadedFile?.let {
//            // @todo request gemini to analyze the image
//            val geminiResult = this.analyzeImageByGemini(uploadedFile.path);
//
//            // @todo calculate points and update them
//            val calculatePointResult = this.calculatePoint(userId)
//
//            // @todo delete the temp file.
//            this.fileManager.deleteFile(uploadedFile.id)

//        return Garbage(
//            id = 0L,
//            name = "Pepsi coke can",
//            category = GarbageCategory.METAL,
//            memo = "일단 보류",
//            pickedUpAt = ZonedDateTime.parse("2024-08-03T02:56:16.914Z")
//        )
//        }
//    }

    fun saveGarbage(garbage: Garbage): Boolean {
        // Fake save logic
        return true
    }
//
//    private fun analyzeImageByGemini(filePath: String): GeminiGarbageResult {
//        val result = this.geminiManager.analyzeImage();
//        // @todo parsing logic
//        return GeminiGarbageResult(
//            name = "plastic bottle",
//            category = GarbageCategory.PLASTIC,
//        )
//    }
//
//    private fun calculatePoint(userId: Long): Boolean {
//        return true
//    }

    fun getGarbage(userId: Long, id: Long): Garbage? {
        val garbageEntity = this.garbageRepository.findById(id)
        if (garbageEntity.isPresent) {
            return Garbage(
                id = garbageEntity.get().id!!,
                name = garbageEntity.get().name,
                memo = garbageEntity.get().memo,
                pickedUpAt = garbageEntity.get().collectedAt,
                latitude = garbageEntity.get().latitude,
                longitude = garbageEntity.get().longitude,
                category = garbageEntity.get().category
            )
        }
        return null
    }
}