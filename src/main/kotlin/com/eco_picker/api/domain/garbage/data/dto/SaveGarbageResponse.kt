package com.eco_picker.api.domain.garbage.data.dto

import com.eco_picker.api.domain.garbage.data.Garbage
import com.eco_picker.api.global.data.BaseResponse

data class SaveGarbageResponse(
    val garbage: Garbage?
) : BaseResponse<BaseResponse.Code>()
