package com.eco_picker.api.global.document

import com.eco_picker.api.global.document.OpenAPIConfig.Companion.JWT
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.security.SecuritySchemes
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@SecuritySchemes(
    SecurityScheme(
        name = JWT,
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        `in` = SecuritySchemeIn.HEADER,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "use with `accessToken`"
    )
)
@Configuration
class OpenAPIConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
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


    companion object {
        const val JWT = "Json Web Token";
    }

}
