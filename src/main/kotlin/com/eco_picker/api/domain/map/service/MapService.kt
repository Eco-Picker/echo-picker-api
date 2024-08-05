package com.eco_picker.api.domain.map.service

import com.eco_picker.api.domain.garbage.repository.GarbageRepository
import com.eco_picker.api.domain.map.data.GarbageLocation
import org.springframework.stereotype.Service

@Service
class MapService(private val garbageRepository: GarbageRepository) {
    fun getMyGarbageLocations(userId: Long): List<GarbageLocation> {
        val garbageEntities = garbageRepository.findByUserId(userId = userId)
        return garbageEntities.map {
            GarbageLocation(
                garbageId = it?.id!!,
                garbageCategory = it.category,
                latitude = it.latitude,
                longitude = it.longitude
            )
        }
    }
}