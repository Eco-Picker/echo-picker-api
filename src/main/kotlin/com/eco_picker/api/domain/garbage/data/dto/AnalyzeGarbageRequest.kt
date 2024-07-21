package com.eco_picker.api.domain.garbage.data.dto

import com.eco_picker.api.domain.garbage.constant.GarbageCategory
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile

data class AnalyzeGarbageRequest(
    @field:Schema(description = "Garbage category selected by User")
    val category: GarbageCategory,

    @field:Schema(description = "Garbage image")
    val image: MultipartFile
)
