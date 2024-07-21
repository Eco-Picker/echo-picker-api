package com.eco_picker.api.global.support

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(private val passwordEncoder: PasswordEncoder) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        // @todo db 연결 후 실제 비즈니스 로직 구현
        if (username == "Jane") {
            return User.builder()
                .username("Jane")
                .password(passwordEncoder.encode("1q2w3e4rA1345!+"))
                .roles("USER")
                .build()
        }
        throw UsernameNotFoundException("User not found")
    }
}