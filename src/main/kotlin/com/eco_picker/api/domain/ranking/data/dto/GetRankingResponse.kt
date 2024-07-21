package com.eco_picker.api.domain.ranking.data.dto

import com.eco_picker.api.domain.ranking.data.Ranking
import com.eco_picker.api.global.data.DefaultResponse

data class GetRankingResponse(val ranking: Ranking) : DefaultResponse()
