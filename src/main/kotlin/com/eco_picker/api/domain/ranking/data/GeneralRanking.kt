package com.eco_picker.api.domain.ranking.data

import io.swagger.v3.oas.annotations.media.Schema

data class GeneralRanking(
    @field:Schema(description = "total items count")
    val totalItems: Long? = 0,

    @field:Schema(description = "total pages count")
    val totalPages: Int? = 0,

    @field:Schema(description = "current page")
    val currentPage: Int? = 1,

    @field:Schema(description = "ranking items for list")
    val rankers: List<GeneralRanker> = emptyList()
)
