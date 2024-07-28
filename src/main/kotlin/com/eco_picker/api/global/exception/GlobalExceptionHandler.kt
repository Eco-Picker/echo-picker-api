package com.eco_picker.api.global.exception

import com.eco_picker.api.global.data.BaseResponse
import com.eco_picker.api.global.data.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors = ex.bindingResult.fieldErrors.map { fieldError ->
            ErrorResponse.FieldError(
                field = fieldError.field,
                message = fieldError.defaultMessage ?: "Validation error"
            )
        }

        val statusCode = HttpStatus.BAD_REQUEST.value()

        val errorResponse = ErrorResponse(status = statusCode, errors = errors).apply {
            code = BaseResponse.Code.VALIDATION_FAILED
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

}