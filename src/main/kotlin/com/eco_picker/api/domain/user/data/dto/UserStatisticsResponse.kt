package com.eco_picker.api.domain.user.data.dto

import com.eco_picker.api.global.data.BaseResponse

class UserStatisticsResponse : BaseResponse<UserStatisticsResponse.Code>() {
    var userStatistics: UserStatistics? = null

    enum class Code {
        USER_NOT_FOUND,
        INTERNAL_SERVER_ERROR
    }

    data class UserStatistics(
        val count: Count,
        val score: Score
    )

    data class Count(
        val totalCount: Int,
        val totalDailyCount: Int,
        val totalWeeklyCount: Int,
        val totalMonthlyCount: Int,
        val totalCardboardPaper: Int,
        val totalPlastic: Int,
        val totalGlass: Int,
        val totalOther: Int,
        val totalMetal: Int,
        val totalFoodScraps: Int,
    )

    data class Score(
        val totalScore: Int,
        val cardboardPaperScore: Int,
        val plasticScore: Int,
        val glassScore: Int,
        val otherScore: Int,
        val metalScore: Int,
        val foodScrapsScore: Int,
    )
}
