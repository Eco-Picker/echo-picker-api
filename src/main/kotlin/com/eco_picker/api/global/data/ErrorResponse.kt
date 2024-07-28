package com.eco_picker.api.global.data

data class ErrorResponse(val status: Int, val errors: List<FieldError>) : DefaultResponse() {
    data class FieldError(
        val field: String,
        val message: String
    )
}
