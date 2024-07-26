package com.eco_picker.api.global.support

import com.eco_picker.api.domain.user.data.Jwt
import com.eco_picker.api.global.data.properties.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Component
class JwtManager(private val jwtProperties: JwtProperties) {
    private val accessTokenSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    private val refreshTokenSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateAccessToken(username: String): Jwt {
        return generateToken(
            username = username,
            validityInMillis = jwtProperties.accessTokenValidity,
            secret = accessTokenSecret
        )
    }

    fun generateRefreshToken(username: String): Jwt {
        return generateToken(
            username = username,
            validityInMillis = jwtProperties.refreshTokenValidity,
            secret = refreshTokenSecret
        )
    }

    private fun generateToken(username: String, validityInMillis: Long, secret: java.security.Key): Jwt {
        val now = Date()
        val validityDate = Date(now.time + validityInMillis)
        val zoneId = ZoneId.systemDefault()

        val token = Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(validityDate)
            .signWith(secret)
            .compact()

        return Jwt(
            token = token,
            expiresAt = ZonedDateTime.ofInstant(validityDate.toInstant(), zoneId),
            issuedAt = ZonedDateTime.ofInstant(now.toInstant(), zoneId)
        )

    }

    fun validateAccessToken(token: String): Boolean {
        return validateToken(token, accessTokenSecret)
    }

    fun validateRefreshToken(token: String): Boolean {
        return validateToken(token, refreshTokenSecret)
    }

    private fun validateToken(token: String, secret: java.security.Key): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getUsernameFromAccessToken(token: String): String? {
        return getUsernameFromToken(token, accessTokenSecret)
    }

    fun getUsernameFromRefreshToken(token: String): String? {
        return getUsernameFromToken(token, refreshTokenSecret)
    }

    private fun getUsernameFromToken(token: String, secret: java.security.Key): String? {
        return Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }

}