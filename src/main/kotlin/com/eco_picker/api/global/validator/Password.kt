package com.eco_picker.api.global.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordValidator::class])
annotation class Password(
    val message: String = "Password must be at least 8 characters long and include at least three of the following: uppercase letters, lowercase letters, numbers and special characters.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

class PasswordValidator : ConstraintValidator<Password, String> {
    override fun initialize(constraintAnnotation: Password) {
        super.initialize(constraintAnnotation)
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return value != null && isValidPassword(value)
    }

    private fun isValidPassword(password: String): Boolean {
        if (password !== "" && 8 <= password.length) {
            var count = 0
            if (Regex("\\d").containsMatchIn(password)) {
                count++
            }
            if (Regex("[a-z]").containsMatchIn(password)) {
                count++
            }
            if (Regex("[A-Z]").containsMatchIn(password)) {
                count++
            }
            if (Regex("[*.!@#%^&(){}\\[\\]:\"'<>,?/~`_+\\-=|\\\\]").containsMatchIn(password)) {
                count++
            }
            return count >= 3

        } else {
            return false
        }
    }
}