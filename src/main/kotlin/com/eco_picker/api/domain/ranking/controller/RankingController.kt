package com.eco_picker.api.domain.ranking.controller

import com.eco_picker.api.domain.ranking.data.dto.GetRankingResponse
import com.eco_picker.api.domain.ranking.data.dto.GetRankerDetailResponse
import com.eco_picker.api.domain.ranking.service.RankingService
import com.eco_picker.api.global.data.BaseListRequest
import com.eco_picker.api.global.data.UserPrincipal
import com.eco_picker.api.global.document.OpenAPIConfig.Companion.JWT
import com.eco_picker.api.global.document.OperationTag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.ZonedDateTime

@RestController()
class RankingController(private val rankingService: RankingService) {
    @Operation(
        tags = [OperationTag.RANKING],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get rankers by total score",
    )
    @PostMapping("/ranking")
    fun getRanking(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody baseListRequest: BaseListRequest
    ): GetRankingResponse {
        val ranking = rankingService.getRanking(offset = baseListRequest.offset, limit = baseListRequest.limit)
        return GetRankingResponse(ranking = ranking).apply { result = true }
    }

    @Operation(
        tags = [OperationTag.RANKING],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get a ranker",
    )
    @GetMapping("/ranker/{userId}")
    fun getRankerDetail(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable("userId") userId: String
    ): GetRankerDetailResponse {
        val rankerDetail = rankingService.getRankerDetail(userId.toLong())
        return if (rankerDetail != null) {
            GetRankerDetailResponse(rankerDetail = rankerDetail).apply {
                result = true
                timestamp = ZonedDateTime.now()
            }
        } else {
            GetRankerDetailResponse().apply {
                result = false
                message = "User not found with id: $userId"
                code = GetRankerDetailResponse.Code.USER_NOT_FOUND
                timestamp = ZonedDateTime.now()
            }
        }
    }
}
