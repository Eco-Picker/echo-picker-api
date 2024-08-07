package com.eco_picker.api.domain.ranking.data.dto

import com.eco_picker.api.global.data.BaseResponse

class RankerStatisticsResponse : BaseResponse<RankerStatisticsResponse.Code>() {
    var rankerStatistics: RankerStatistics? = null

    enum class Code {
        USER_NOT_FOUND,
        INTERNAL_SERVER_ERROR
    }

    data class RankerStatistics(
        val count: Count,
        val score: Score
    )

    data class Count(
        val totalCount: Int,
        val totalCardboardPaper: Int,
        val totalPlastic: Int,
        val totalGlass: Int,
        val totalOther: Int,
        val totalMetal: Int,
        val totalFoodScraps: Int
    )

    data class Score(
        val totalScore: Int,
        val cardboardPaperScore: Int,
        val plasticScore: Int,
        val glassScore: Int,
        val otherScore: Int,
        val metalScore: Int,
        val foodScrapsScore: Int
    )
}
