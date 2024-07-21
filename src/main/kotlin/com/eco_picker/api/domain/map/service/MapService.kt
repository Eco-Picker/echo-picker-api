package com.eco_picker.api.domain.map.service

import com.eco_picker.api.domain.garbage.constant.GarbageCategory
import com.eco_picker.api.domain.garbage.data.Garbage
import com.eco_picker.api.domain.map.data.Location
import com.eco_picker.api.domain.map.data.LocationDetail
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class MapService {
    fun getMyLocations(userId: Long): List<Location> {
        return listOf(
            Location(
                id = 1L,
                longitude = "",
                latitude = ""
            ),
            Location(
                id = 2L,
                longitude = "",
                latitude = ""
            )
        )
    }

    fun getMyLocation(userId: Long, id: Long): LocationDetail? {
        return LocationDetail(
            id = 1L,
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