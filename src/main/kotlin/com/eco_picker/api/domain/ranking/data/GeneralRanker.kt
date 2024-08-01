package com.eco_picker.api.domain.ranking.data

import io.swagger.v3.oas.annotations.media.Schema

data class GeneralRanker (
    @field:Schema(description = "user ID(PK)")
    val id: Long?,

    @field:Schema(description = "username")
    val username: String,

    @field:Schema(description = "point")
    val point: Int
)





