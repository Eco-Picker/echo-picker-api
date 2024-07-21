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
                    .description(
                        "This API is based on the Eco Picker project.\n" +
                                "- Many APIs are currently dummy implementations, provided to offer interfaces as soon as possible. Refer only to the request and response formats.\n" +
                                "- Only `POST` and `GET` methods are supported.\n" +
                                "- Common response format:\n" +
                                "  ```json\n" +
                                "  {\n" +
                                "    \"result\": boolean,\n" +
                                "    \"message\": string?, // Present only if an error occurs\n" +
                                "    \"code\": string?, // Present only if an error occurs\n" +
                                "    \"timestamp\": datetime,\n" +
                                "    ...\n" +
                                "  }\n" +
                                "[TODO] Domain: `\"https://eco-picker.com\"`, `\"https://eco-picker.io\"`, or other domains.\n"
                    )
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
