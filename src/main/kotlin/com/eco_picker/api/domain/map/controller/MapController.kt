package com.eco_picker.api.domain.map.controller

import com.eco_picker.api.domain.map.data.dto.GetMyGarbageLocationsResponse
import com.eco_picker.api.domain.map.service.MapService
import com.eco_picker.api.global.data.UserPrincipal
import com.eco_picker.api.global.document.OpenAPIConfig.Companion.JWT
import com.eco_picker.api.global.document.OperationTag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MapController(private val mapService: MapService) {
    @Operation(
        tags = [OperationTag.MAP],
        security = [SecurityRequirement(name = JWT)],
        summary = "Get My Garbage locations",
    )
    @GetMapping("/maps")
    fun getMyGarbageLocations(@AuthenticationPrincipal principal: UserPrincipal): GetMyGarbageLocationsResponse {
        val locations = this.mapService.getMyGarbageLocations(userId = principal.id)
        return GetMyGarbageLocationsResponse(garbageLocations = locations).apply {
            result = true
        }
    }
}