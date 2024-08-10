package com.eco_picker.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@ConfigurationPropertiesScan(
    basePackages = [
        "com.eco_picker.api",
    ]
)
@SpringBootApplication
@EnableScheduling
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
