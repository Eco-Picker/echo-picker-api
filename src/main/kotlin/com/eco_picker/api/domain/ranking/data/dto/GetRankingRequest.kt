package com.eco_picker.api.domain.ranking.data.dto

import io.swagger.v3.oas.annotations.media.Schema

data class GetRankingRequest(
    @field:Schema(description = "limit", defaultValue = "0")
    val limit: Int = 0,

    @field:Schema(description = "offset", defaultValue = "10")
    val offset: Int = 10,
)
