package com.eco_picker.api.domain.ranking.service

import com.eco_picker.api.domain.ranking.constant.RankingPeriod
import com.eco_picker.api.domain.ranking.data.Ranker
import com.eco_picker.api.domain.ranking.data.Ranking
import org.springframework.stereotype.Service

@Service
class RankingService {
    fun getDailyRanking(userId: Long): Ranking {
        return this.getRanking(userId, RankingPeriod.DAILY)
    }

    fun getWeeklyRanking(userId: Long): Ranking {
        return this.getRanking(userId, RankingPeriod.WEEKLY)
    }

    fun getMonthlyRanking(userId: Long): Ranking {
        return this.getRanking(userId, RankingPeriod.MONTHLY)
    }

    private fun getRanking(userId: Long, period: RankingPeriod): Ranking {
        return when (period) {
            RankingPeriod.DAILY -> Ranking(
                rankers = listOf(
                    Ranker(id = 1L, username = "Alice", point = 1200),
                    Ranker(id = 2L, username = "Bob", point = 1150),
                    Ranker(id = 3L, username = "Charlie", point = 1100),
                    Ranker(id = 4L, username = "David", point = 1000),
                    Ranker(id = 5L, username = "Eve", point = 950)
                )
            )

            RankingPeriod.WEEKLY -> Ranking(
                rankers = listOf(
                    Ranker(id = 1L, username = "Alice", point = 7000),
                    Ranker(id = 2L, username = "Bob", point = 6800),
                    Ranker(id = 3L, username = "Charlie", point = 6700),
                    Ranker(id = 4L, username = "David", point = 6600),
                    Ranker(id = 5L, username = "Eve", point = 6500)
                )
            )

            RankingPeriod.MONTHLY -> Ranking(
                rankers = listOf(
                    Ranker(id = 1L, username = "Alice", point = 30000),
                    Ranker(id = 2L, username = "Bob", point = 29000),
                    Ranker(id = 3L, username = "Charlie", point = 28000),
                    Ranker(id = 4L, username = "David", point = 27000),
                    Ranker(id = 5L, username = "Eve", point = 26000)
                )
            )
        }
    }
}