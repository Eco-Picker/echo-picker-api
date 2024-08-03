package com.eco_picker.api.domain.garbage.controller

import com.eco_picker.api.domain.garbage.data.Garbage
import com.eco_picker.api.domain.garbage.constant.GarbageCategory
import com.eco_picker.api.domain.garbage.data.dto.AnalyzeGarbageRequest
import com.eco_picker.api.domain.garbage.data.dto.AnalyzeGarbageResponse
import com.eco_picker.api.domain.garbage.data.dto.SaveGarbageRequest
import com.eco_picker.api.domain.garbage.data.dto.SaveGarbageResponse
import com.eco_picker.api.domain.garbage.service.GarbageService
import com.eco_picker.api.global.data.UserPrincipal
import com.eco_picker.api.global.data.BaseResponse
import com.eco_picker.api.global.document.OpenAPIConfig.Companion.JWT
import com.eco_picker.api.global.document.OperationTag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class GarbageController(private val garbageService: GarbageService) {
    @Operation(
        tags = [OperationTag.GARBAGE],
        security = [SecurityRequirement(name = JWT)],
        summary = "Analyze an image"
    )
    @PostMapping("/garbage/analyze", consumes = ["application/json"]) // Use JSON
    fun analyzeGarbage(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody analyzeGarbageRequest: AnalyzeGarbageRequest
    ): AnalyzeGarbageResponse {
        // Simulate image analysis using a string
        val garbage = garbageService.analyzeImage(
            userId = principal.id,
            category = analyzeGarbageRequest.category,
            imageData = analyzeGarbageRequest.image // Pass string instead of file
        )

        // Return the fake response
        return AnalyzeGarbageResponse(garbage = garbage).apply {
            result = true
            timestamp = ZonedDateTime.now()
            message = "string"
            code = BaseResponse.Code.VALIDATION_FAILED
        }
    }

    @Operation(
        tags = [OperationTag.GARBAGE],
        security = [SecurityRequirement(name = JWT)],
        summary = "Save garbage data"
    )
    @PostMapping("/garbage/save", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveGarbage(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody saveGarbageRequest: SaveGarbageRequest
    ): SaveGarbageResponse {
        val garbage = saveGarbageRequest.garbage
        // Return fake data for front-end testing
        return SaveGarbageResponse(
            garbage = Garbage(
                id = garbage.id,
                name = garbage.name,
                category = garbage.category,
                memo = garbage.memo,
                pickedUpAt = garbage.pickedUpAt,
                latitude = garbage.latitude, // Use provided latitude
                longitude = garbage.longitude // Use provided longitude
            )
        ).apply {
            result = true
            timestamp = ZonedDateTime.now()
            message = "Garbage saved successfully"
            code = BaseResponse.Code.VALIDATION_FAILED
        }
    }
}