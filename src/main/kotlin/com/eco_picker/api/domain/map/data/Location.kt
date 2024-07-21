package com.eco_picker.api.domain.map.data

import io.swagger.v3.oas.annotations.media.Schema

data class Location(
    @field:Schema(description = "id")
    val id: Long,

    @field:Schema(description = "Longitude of the location")
    val longitude: String,

    @field:Schema(description = "Latitude of the location")
    val latitude: String
)
