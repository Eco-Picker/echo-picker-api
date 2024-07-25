package com.eco_picker.api.domain.user.repository

import com.eco_picker.api.domain.user.data.User
import org.springframework.data.jpa.repository.JpaRepository

// Deal with CRUD operations regarding User entity

interface UserRepository : JpaRepository<User, String> {
    fun findByUsername(username: String): User?
}