package com.eco_picker.api.domain.garbage.data.dto

import com.eco_picker.api.domain.garbage.data.Garbage
import com.eco_picker.api.global.data.DefaultResponse

data class GetGarbageResponse(val garbage: Garbage? = null) : DefaultResponse()
