package com.eco_picker.api.domain.map.data.dto

import com.eco_picker.api.domain.map.data.GarbageLocationDetail
import com.eco_picker.api.global.data.DefaultResponse
import io.swagger.v3.oas.annotations.media.Schema

data class GetMyGarbageLocationDetailResponse(
    @field:Schema(description = "locations with a garbage information", nullable = true)
    val garbageLocationDetail: GarbageLocationDetail?
) : DefaultResponse()
