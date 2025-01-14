package com.eco_picker.api.domain.ranking.data

import io.swagger.v3.oas.annotations.media.Schema
import com.eco_picker.api.domain.ranking.data.dto.RankerStatisticsResponse

data class Ranker(
    @field:Schema(description = "user ID(PK)")
    val id: Long?,

    @field:Schema(description = "username")
    val username: String,

    @field:Schema(description = "ranker statistics")
    val rankerStatistics: RankerStatisticsResponse.RankerStatistics
)
