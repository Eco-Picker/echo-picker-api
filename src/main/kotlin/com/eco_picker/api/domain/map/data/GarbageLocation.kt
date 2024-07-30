package com.eco_picker.api.domain.map.data

import com.eco_picker.api.domain.garbage.constant.GarbageCategory
import io.swagger.v3.oas.annotations.media.Schema

data class GarbageLocation(
    @field:Schema(description = "garbage ID (PK)")
    val garbageId: Long,

    @field:Schema(description = "garbage category")
    val garbageCategory: GarbageCategory,

    @field:Schema(description = "Longitude of the location")
    val longitude: String,

    @field:Schema(description = "Latitude of the location")
    val latitude: String
)
