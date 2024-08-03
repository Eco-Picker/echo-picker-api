package com.eco_picker.api.global.config

import com.eco_picker.api.global.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig() {
    @Bean
    fun jwtRequestFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors { cors ->
            cors.configurationSource(corsConfigurationSource())
        }.authorizeHttpRequests { authorize ->
            authorize.requestMatchers(
                "/api/swagger-ui/**",
                "/api/v3/api-docs/**",
                "/api/swagger-resources/**",
                "/api/p/**",
                "/actuator/**",
                "/email-verification-success.html",
                "/email-verification-failed.html"
            ).permitAll()
                .anyRequest()
                .authenticated()
        }.csrf { csrf ->
            csrf.disable()
        }.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }


    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*") // @todo front 도메인 확정나면 제한
        configuration.allowedMethods = listOf("GET", "POST", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.maxAge = 3600L
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}