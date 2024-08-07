package com.eco_picker.api.domain.ranking.data.dto

import com.eco_picker.api.global.data.BaseResponse
import com.eco_picker.api.domain.ranking.data.Ranker
import io.swagger.v3.oas.annotations.media.Schema

data class GetRankerDetailResponse(
    @field:Schema(description = "Ranker detail", nullable = true)
    val rankerDetail: Ranker? = null
) : BaseResponse<GetRankerDetailResponse.Code>() {

    enum class Code {
        USER_NOT_FOUND
    }
}