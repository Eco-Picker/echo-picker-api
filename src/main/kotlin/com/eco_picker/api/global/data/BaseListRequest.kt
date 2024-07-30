package com.eco_picker.api.global.data

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Max

open class BaseListRequest {
    @Schema(
        description = "The starting position of the data retrieval",
        defaultValue = "0",
        example = "0"
    )
    val offset: Int = 0

    @Schema(
        description = "The maximum number of results per request",
        defaultValue = "10",
        example = "10",
        maximum = "100"
    )
    @field:Max(100)
    val limit: Int = 10
}