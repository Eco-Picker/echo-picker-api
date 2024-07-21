package com.eco_picker.api.domain.map.data.dto

import com.eco_picker.api.domain.map.data.Location
import com.eco_picker.api.global.data.DefaultResponse
import io.swagger.v3.oas.annotations.media.Schema

data class GetMyLocationsResponse(
    @field:Schema(description = "locations")
    val locations: List<Location> = emptyList()
) : DefaultResponse()