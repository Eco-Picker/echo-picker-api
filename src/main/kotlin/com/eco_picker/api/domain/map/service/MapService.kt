package com.eco_picker.api.domain.map.service

import com.eco_picker.api.domain.garbage.constant.GarbageCategory
import com.eco_picker.api.domain.garbage.data.Garbage
import com.eco_picker.api.domain.map.data.GarbageLocation
import com.eco_picker.api.domain.map.data.GarbageLocationDetail
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class MapService {
    fun getMyGarbageLocations(userId: Long): List<GarbageLocation> {
        return listOf(
            GarbageLocation(
                garbageId = 1L,
                garbageCategory = GarbageCategory.GLASS,
                longitude = "",
                latitude = ""
            ),
            GarbageLocation(
                garbageId = 2L,
                garbageCategory = GarbageCategory.GLASS,
                longitude = "",
                latitude = ""
            )
        )
    }

    fun getMyGarbageLocation(userId: Long, garbageId: Long): GarbageLocationDetail? {
        return GarbageLocationDetail(
            longitude = "",
            latitude = "",
            garbage = Garbage(
                id = 1L,
                name = "",
                category = GarbageCategory.GLASS,
                memo = null,
                pickedUpAt = ZonedDateTime.now()
            )
        )
    }
}