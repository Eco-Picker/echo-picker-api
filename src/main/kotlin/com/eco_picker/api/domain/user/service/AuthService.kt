package com.eco_picker.api.domain.user.service

import com.eco_picker.api.domain.mail.service.MailService
import com.eco_picker.api.domain.user.constant.OnboardingStatus
import com.eco_picker.api.domain.user.data.dto.*
import com.eco_picker.api.domain.user.data.entity.UserEmailVerificationEntity
import com.eco_picker.api.domain.user.data.entity.UserEntity
import com.eco_picker.api.domain.user.repository.UserEmailVerificationRepository
import com.eco_picker.api.domain.user.repository.UserRepository
import com.eco_picker.api.global.support.JwtManager
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.util.*

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val jwtManager: JwtManager,
    private val mailService: MailService,
    private val userRepository: UserRepository,
    private val userEmailVerifyRepository: UserEmailVerificationRepository,
    private val passwordEncoder: PasswordEncoder
) {
    private val logger = KotlinLogging.logger { }

    fun signup(signupRequest: SignupRequest): SignupResponse {
        val (username, password, email) = signupRequest

        try {
            val userEntity = userRepository.findByUsernameOrEmail(username = username, email = email)
            userEntity?.let {
                if (it.username == username) {
                    return SignupResponse().apply {
                        code = SignupResponse.Code.ALREADY_REGISTERED_USERNAME
                    }
                }
                if (it.email == email) {
                    return SignupResponse().apply {
                        code = SignupResponse.Code.ALREADY_REGISTERED_EMAIL
                    }
                }
                if (it.onboardingStatus == OnboardingStatus.PENDING_VERIFY) {
                    return SignupResponse().apply {
                        code = SignupResponse.Code.PENDING_VERIFY_EMAIL
                    }
                }
            }

            val newUserEntity = userRepository.save(
                UserEntity(
                    username = username,
                    email = email,
                    password = passwordEncoder.encode(password),
                    onboardingStatus = OnboardingStatus.BEGIN,
                )
            )

            if (newUserEntity?.id == null) {
                throw Exception("Failed to create user")
            }

            val verifyMailToken = UUID.randomUUID().toString()
            userEmailVerifyRepository.save(
                UserEmailVerificationEntity(
                    token = verifyMailToken,
                    userId = newUserEntity.id,
                    issuedAt = ZonedDateTime.now()
                )
            )

            mailService.sendVerify(username = username, email = email, token = verifyMailToken)
            return SignupResponse().apply {
                result = true
            }

        } catch (e: Exception) {
            logger.error(e) { "failed to sign up $username" }
            return SignupResponse().apply {
                message = e.message
            }
        }
    }

    fun login(loginRequest: LoginRequest): LoginResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )
        val username = authentication.name
        val accessToken = jwtManager.generateAccessToken(username = username)
        val refreshToken = jwtManager.generateRefreshToken(username = username)

        return LoginResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        ).apply {
            result = true
        }
    }

    fun renewAccessToken(userId: Long, refreshToken: String): String? {
        // @todo db 에서도 유효성 검사 (by userId, token)
        if (jwtManager.validateRefreshToken(refreshToken)) {
            val username = jwtManager.getUsernameFromRefreshToken(refreshToken)
            username?.let {
                val newAccessToken = jwtManager.generateAccessToken(it)
                return@let newAccessToken
            }
        }
        return null
    }

    fun logout(userId: Long): Boolean {
        return true
    }

    fun verifyMail(token: String): VerifyMailResponse {
        try {
            val userEmailVerificationEntity = userEmailVerifyRepository.findByToken(token)
                ?: return VerifyMailResponse().apply {
                    code = VerifyMailResponse.Code.INVALIDATED_TOKEN
                }

            userEmailVerificationEntity.let {
                if (it.verifiedAt != null) {
                    return VerifyMailResponse().apply {
                        code = VerifyMailResponse.Code.ALREADY_VERIFIED_USER
                    }
                }
                if (it.issuedAt.plusMinutes(10).isBefore(ZonedDateTime.now())) {
                    return VerifyMailResponse().apply {
                        code = VerifyMailResponse.Code.INVALIDATED_TOKEN
                    }
                }
                val userEntity = userRepository.findById(it.userId)
                if (userEntity.isEmpty) {
                    return VerifyMailResponse().apply {
                        code = VerifyMailResponse.Code.INVALIDATED_TOKEN
                    }
                }

                userEmailVerifyRepository.save(it.copy(verifiedAt = ZonedDateTime.now()))
                userRepository.save(userEntity.get().copy(onboardingStatus = OnboardingStatus.COMPLETE))
            }
        } catch (e: Exception) {
            logger.error(e) { "failed to verify email verification" }
            return VerifyMailResponse().apply {
                message = e.message
            }
        }

        return VerifyMailResponse().apply {
            result = true
        }
    }

    fun sendTempPassword(email: String): Boolean {
        val tempPassword = this.generateTempPassword()
        val username = "" // @todo get from db
        // @todo send mail
        mailService.sendTempPassword(username = username, email = email, password = tempPassword)
        return true
    }

    private fun generateTempPassword(): String {
        return "temp password"
    }
}