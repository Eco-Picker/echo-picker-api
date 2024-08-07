package com.eco_picker.api.domain.garbage.data.dto

import com.eco_picker.api.domain.garbage.data.SaveGarbage
import io.swagger.v3.oas.annotations.media.Schema

data class SaveGarbageRequest(
    @field:Schema(description = "Garbage data provided by user", required = true)
    val garbage: SaveGarbage
)
