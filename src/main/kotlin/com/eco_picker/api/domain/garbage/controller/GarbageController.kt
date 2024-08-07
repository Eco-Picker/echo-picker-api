package com.eco_picker.api.domain.garbage.controller

import com.eco_picker.api.domain.garbage.data.Garbage
import com.eco_picker.api.domain.garbage.data.dto.*
import com.eco_picker.api.domain.garbage.service.GarbageService
import com.eco_picker.api.global.data.BaseResponse
import com.eco_picker.api.global.data.UserPrincipal
import com.eco_picker.api.global.document.OpenAPIConfig.Companion.JWT
import com.eco_picker.api.global.document.OperationTag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import com.eco_picker.api.domain.garbage.constant.GarbageCategory
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.ZonedDateTime

@RestController
class GarbageController(private val garbageService: GarbageService) {
//    @Operation(
//        tags = [OperationTag.GARBAGE],
//        security = [SecurityRequirement(name = JWT)],
//        summary = "Analyze an image"
//    )
//    @PostMapping("/garbage/analyze", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
//    fun analyzeGarbage(
//        @AuthenticationPrincipal principal: UserPrincipal,
//        @RequestPart("file") file: MultipartFile
//    ): AnalyzeGarbageResponse {
//        val garbage = garbageService.analyzeImage(
//            userId = principal.id,
//            file = file
//        )
//
//        return if (garbage != null) {
//            AnalyzeGarbageResponse(
//                result = true,
//                garbage = garbage,
//                timestamp = ZonedDateTime.now()
//            )
//        } else {
//            AnalyzeGarbageResponse(
//                result = false,
//                garbage = null,
//                message = "Failed to analyze the image",
//                code = "ANALYSIS_FAILED",
//                timestamp = ZonedDateTime.now()
//            )
//        }
//    }
    @Operation(
        tags = [OperationTag.GARBAGE],
        security = [SecurityRequirement(name = JWT)],
        summary = "Analyze an image"
    )
    @PostMapping("/analyze", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun analyzeGarbage(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestPart("file") file: MultipartFile
    ): AnalyzeGarbageResponse {
        val garbage = garbageService.analyzeImage(
            userId = principal.id,
            file = file
        )

        return AnalyzeGarbageResponse(
            result = garbage != null,
            garbage = garbage,
            timestamp = ZonedDateTime.now(),
            message = if (garbage == null) "Failed to analyze the image" else null,
            code = if (garbage == null) "ANALYSIS_FAILED" else null
        )
    }

//    @Operation(
//        tags = [OperationTag.GARBAGE],
//        security = [SecurityRequirement(name = JWT)],
//        summary = "Save garbage data"
//    )
//    @PostMapping("/garbage/save", consumes = [MediaType.APPLICATION_JSON_VALUE])
//    fun saveGarbage(
//        @AuthenticationPrincipal principal: UserPrincipal,
//        @RequestBody saveGarbageRequest: SaveGarbageRequest
//    ): SaveGarbageResponse {
//        val garbage = saveGarbageRequest.garbage.copy(userId = principal.id)
//        val saveResult = garbageService.saveGarbage(garbage)
//
//        return SaveGarbageResponse(
//            garbage = if (saveResult) garbage else null
//        ).apply {
//            result = saveResult
//            timestamp = ZonedDateTime.now()
//            message = if (saveResult) null else "Failed to save garbage data"
//            code = if (saveResult) null else BaseResponse.Code.VALIDATION_FAILED
//        }
//    }

    @Operation(
        tags = [OperationTag.GARBAGE],
        security = [SecurityRequirement(name = JWT)],
        summary = "Save garbage data"
    )
    @PostMapping("/save", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveGarbage(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody saveGarbageRequest: SaveGarbageRequest
    ): SaveGarbageResponse {
        val garbage = saveGarbageRequest.garbage.copy(userId = principal.id)
        val saveResult = garbageService.saveGarbage(garbage)

        return SaveGarbageResponse(
            garbage = if (saveResult) garbage else null
        ).apply {
            result = saveResult
            timestamp = ZonedDateTime.now()
            message = if (saveResult) null else "Failed to save garbage data"
            code = if (saveResult) null else BaseResponse.Code.VALIDATION_FAILED
        }
    }

    @Operation(
        tags = [OperationTag.GARBAGE],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get a garbage"
    )
    @GetMapping("/garbage/{id}")
    fun getGarbage(
        @AuthenticationPrincipal principal: UserPrincipal,
        @Parameter(description = "garbage ID (PK)") @PathVariable id: Long
    ): GetGarbageResponse {
        val garbage = this.garbageService.getGarbage(userId = principal.id, id = id)
            ?: return GetGarbageResponse().apply {
                code = BaseResponse.Code.VALIDATION_FAILED
                message = "not found a garbage"
            }

        return GetGarbageResponse(garbage = garbage).apply {
            result = true
        }
    }
}