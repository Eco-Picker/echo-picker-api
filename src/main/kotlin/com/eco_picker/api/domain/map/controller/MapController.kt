package com.eco_picker.api.domain.map.controller

import com.eco_picker.api.domain.map.data.dto.GetMyLocationDetailResponse
import com.eco_picker.api.domain.map.data.dto.GetMyLocationsResponse
import com.eco_picker.api.domain.map.service.MapService
import com.eco_picker.api.global.data.UserPrincipal
import com.eco_picker.api.global.document.OpenAPIConfig.Companion.JWT
import com.eco_picker.api.global.document.OperationTag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MapController(private val mapService: MapService) {
    @Operation(
        tags = [OperationTag.MAP],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get My locations",
    )
    @GetMapping("/maps")
    fun getMyLocations(@AuthenticationPrincipal principal: UserPrincipal): GetMyLocationsResponse {
        val locations = this.mapService.getMyLocations(principal.id)
        return GetMyLocationsResponse(locations = locations).apply {
            result = true
        }
    }

    @Operation(
        tags = [OperationTag.MAP],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get My specific location",
    )
    @GetMapping("/maps/{id}")
    fun getMyLocation(
        @AuthenticationPrincipal principal: UserPrincipal,
        @Parameter(description = "location ID")
        @PathVariable id: Long
    ): GetMyLocationDetailResponse {
        val locationDetail = this.mapService.getMyLocation(principal.id, id)
        return GetMyLocationDetailResponse(locationDetail = locationDetail).apply {
            result = true
        }
    }
}