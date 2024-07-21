package com.eco_picker.api.domain.garbage.service

import com.eco_picker.api.domain.garbage.constant.GarbageCategory
import com.eco_picker.api.domain.garbage.data.Garbage
import com.eco_picker.api.domain.garbage.data.GeminiGarbageResult
import com.eco_picker.api.global.support.FileManager
import com.eco_picker.api.global.support.GeminiManager
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.ZonedDateTime

@Service
class GarbageService(private val fileManager: FileManager, private val geminiManager: GeminiManager) {

    fun analyzeImage(userId: Long, category: GarbageCategory, file: MultipartFile): Garbage? {
        // @todo 업로드 이미지
        val uploadedFile = this.fileManager.saveFile(file)

        uploadedFile?.let {
            // @todo request gemini to analyze the image
            val geminiResult = this.analyzeImageByGemini(uploadedFile.path);

            // @todo calculate points and update them
            val calculatePointResult = this.calculatePoint(userId)

            // @todo delete the temp file.
            this.fileManager.deleteFile(uploadedFile.id)

            return Garbage(
                id = 1L,
                name = geminiResult.name,
                category = geminiResult.category,
                memo = null,
                pickedUpAt = ZonedDateTime.now()
            )
        }

        return null
    }

    private fun analyzeImageByGemini(filePath: String): GeminiGarbageResult {
        val result = this.geminiManager.analyzeImage();
        // @todo parsing logic
        return GeminiGarbageResult(
            name = "plastic bottle",
            category = GarbageCategory.PLASTIC,
        )
    }

    private fun calculatePoint(userId: Long): Boolean {
        return true
    }
}