package com.eco_picker.api.global.filter

import com.eco_picker.api.domain.user.repository.AuthRepository
import com.eco_picker.api.global.data.UserPrincipal
import com.eco_picker.api.global.support.JwtManager
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter() : OncePerRequestFilter() {
    @Autowired
    private lateinit var jwtManager: JwtManager

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var authRepository: AuthRepository

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = getAccessTokenFromRequest(request)

        if (token != null && jwtManager.validateAccessToken(token)) {
            try {
                val username = jwtManager.getUsernameFromAccessToken(token)
                val userDetails = userDetailsService.loadUserByUsername(username) as UserPrincipal
                authRepository.findByUserIdAndAccessToken(userId = userDetails.id, accessToken = token)
                    ?: throw Exception("Invalid access token")
                
                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                SecurityContextHolder.getContext().authentication = authentication

                logger.debug("User authenticated: $username")
            } catch (ex: UsernameNotFoundException) {
                logger.warn("User not found: ${ex.message}")
            } catch (ex: Exception) {
                logger.error("Authentication error: ${ex.message}", ex)
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun getAccessTokenFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7)
        } else null
    }
}