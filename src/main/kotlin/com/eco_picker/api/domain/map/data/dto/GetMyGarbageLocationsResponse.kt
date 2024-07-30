package com.eco_picker.api.domain.map.data.dto

import com.eco_picker.api.domain.map.data.GarbageLocation
import com.eco_picker.api.global.data.DefaultResponse
import io.swagger.v3.oas.annotations.media.Schema

data class GetMyGarbageLocationsResponse(
    @field:Schema(description = "locations")
    val garbageLocations: List<GarbageLocation> = emptyList(),
) : DefaultResponse()