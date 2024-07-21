package com.eco_picker.api.global.data.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "file")
data class FileProperties(var maxFileSizeMb: Long = 5)