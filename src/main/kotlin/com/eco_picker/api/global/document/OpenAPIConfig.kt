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
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
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
class OpenAPIConfig(
    private val env: Environment,
    @Value("\${general.api-base-url}")
    private val apiBaseUrl: String,
    @Value("\${server.port}")
    private val port: Int,
) {

    @Bean
    fun openAPI(): OpenAPI {
        val openApi = OpenAPI()
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
                                "Prod-Domain: `\"https://eco-picker.com\"`"
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

        val activeProfiles: Array<String> = env.activeProfiles
        val activeProfile = if (activeProfiles.isEmpty()) {
            "default"
        } else {
            activeProfiles[0]
        }

        val baseUrl = apiBaseUrl.replace("/api", "")

        when (activeProfile) {
            "dev" -> {
                openApi.addServersItem(
                    Server().url("http://localhost:$port").description(activeProfile)
                ).addServersItem(
                    Server().url(baseUrl).description("production")
                )
            }

            "production" -> {
                openApi.addServersItem(
                    Server().url(baseUrl).description(activeProfile)
                )
            }
        }
        return openApi
    }


    companion object {
        const val JWT = "Json Web Token"
    }

}
