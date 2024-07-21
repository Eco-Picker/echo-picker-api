package com.eco_picker.api.domain.garbage.controller

import com.eco_picker.api.domain.garbage.data.dto.AnalyzeGarbageRequest
import com.eco_picker.api.domain.garbage.data.dto.AnalyzeGarbageResponse
import com.eco_picker.api.domain.garbage.service.GarbageService
import com.eco_picker.api.global.data.UserPrincipal
import com.eco_picker.api.global.document.OpenAPIConfig.Companion.JWT
import com.eco_picker.api.global.document.OperationTag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GarbageController(private val garbageService: GarbageService) {
    @Operation(
        tags = [OperationTag.GARBAGE],
        security = [SecurityRequirement(name = JWT)],
        summary = "Analyze an image",
    )
    @PostMapping("/garbage/analyze", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun analyzeGarbage(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody analyzeGarbageRequest: AnalyzeGarbageRequest
    ): AnalyzeGarbageResponse {
        val garbage = this.garbageService.analyzeImage(
            userId = principal.id,
            category = analyzeGarbageRequest.category,
            file = analyzeGarbageRequest.image
        )
        return AnalyzeGarbageResponse(garbage = garbage).apply {
            result = true
        }
    }
}