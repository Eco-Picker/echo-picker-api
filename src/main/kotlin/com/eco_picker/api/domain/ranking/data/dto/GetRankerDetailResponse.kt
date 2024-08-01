package com.eco_picker.api.domain.ranking.data.dto

import com.eco_picker.api.domain.ranking.data.Ranker
import com.eco_picker.api.global.data.DefaultResponse

data class GetRankerDetailResponse(
    val rankerDetail: Ranker,
) : DefaultResponse()
