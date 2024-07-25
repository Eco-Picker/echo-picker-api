package com.eco_picker.api.global.support

import com.eco_picker.api.domain.user.repository.UserRepository
import com.eco_picker.api.global.data.UserPrincipal
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(private val userRepository: UserRepository) :
    UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User with username $username not found")

        val authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))

        return UserPrincipal(
            id = userEntity.id!!,
            username = userEntity.username,
            password = userEntity.password,
            authorities = authorities
        )
    }
}