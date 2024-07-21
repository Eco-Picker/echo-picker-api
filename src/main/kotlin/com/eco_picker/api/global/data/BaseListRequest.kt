package com.eco_picker.api.global.data

import io.swagger.v3.oas.annotations.media.Schema

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
        example = "10"
    )
    val limit: Int = 10
}