package com.eco_picker.api.global.data

import java.time.ZonedDateTime

open class BaseResponse<E : Enum<*>> {
    var result: Boolean = false
    var timestamp: ZonedDateTime = ZonedDateTime.now()
    var message: String? = null
    var code: E? = null

    enum class Code {
        VALIDATION_FAILED,
        NOT_CLASSIFIED,
        EXCEPTION
    }
}