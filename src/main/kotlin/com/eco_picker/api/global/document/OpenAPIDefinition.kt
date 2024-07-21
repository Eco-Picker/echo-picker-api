package com.eco_picker.api.global.document

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@Configuration
class OpenAPIDefinition {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .addSecurityItem(SecurityRequirement().addList("Bearer Authentication"))
            .components(
                Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme())
            )
            .info(
                Info().title("Eco Picker API")
                    .description("The Eco Picker API")
                    .version("1.0")
                    .contact(
                        Contact()
                            .name("Mihee Kim")
                            .email("jerry2219398@gmail.com")
                    )
                    .license(
                        License().name("MIT")
                            .url("https://opensource.org/licenses/MIT")
                    )
            )
    }

    private fun createAPIKeyScheme(): SecurityScheme {
        return SecurityScheme().type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer")
            .`in`(SecurityScheme.In.HEADER)
            .name(HttpHeaders.AUTHORIZATION)
            .description("Use with access token.")
    }

}
