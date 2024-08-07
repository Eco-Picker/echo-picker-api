package com.eco_picker.api.domain.garbage.data

import com.eco_picker.api.domain.garbage.constant.GarbageCategory
import io.swagger.v3.oas.annotations.media.Schema
import java.time.ZonedDateTime

data class SaveGarbage(
    @field:Schema(description = "User ID", required = true)
    val userId: Long,

    @field:Schema(description = "name from Gemini", required = true)
    val name: String,

    @field:Schema(description = "category from Gemini", required = true)
    val category: GarbageCategory,

    @field:Schema(description = "picked up datetime", required = true)
    val pickedUpAt: ZonedDateTime,

    @field:Schema(description = "Latitude of the garbage location", required = true)
    val latitude: Double,

    @field:Schema(description = "Longitude of the garbage location", required = true)
    val longitude: Double
)
