package com.eco_picker.api.domain.user.constant

enum class OnboardingStatus(val value: Int) {
    BEGIN(1),
    PENDING_VERIFY(2),
    COMPLETE(3),
}