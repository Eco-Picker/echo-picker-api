package com.eco_picker.api.global.support

import com.eco_picker.api.global.data.properties.JwtProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtManager(private val jwtProperties: JwtProperties) {
    private val accessTokenSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    private val refreshTokenSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateAccessToken(username: String): String {
        return generateToken(username, jwtProperties.accessTokenValidity, accessTokenSecret)
    }

    fun generateRefreshToken(username: String): String {
        return generateToken(username, jwtProperties.refreshTokenValidity, refreshTokenSecret)
    }

    private fun generateToken(username: String, validity: Long, secret: java.security.Key): String {
        val now = Date()
        val validity = Date(now.time + validity)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(secret)
            .compact()
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