package com.eco_picker.api.domain.garbage.data.dto

import com.eco_picker.api.domain.garbage.data.SaveGarbage
import com.eco_picker.api.global.data.BaseResponse

data class SaveGarbageResponse(
    val garbage: SaveGarbage?
) : BaseResponse<BaseResponse.Code>()
