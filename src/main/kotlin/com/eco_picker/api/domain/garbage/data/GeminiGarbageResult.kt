package com.eco_picker.api.domain.garbage.data

import com.eco_picker.api.domain.garbage.constant.GarbageCategory

data class GeminiGarbageResult(
    val name: String,
    val category: GarbageCategory,
)
