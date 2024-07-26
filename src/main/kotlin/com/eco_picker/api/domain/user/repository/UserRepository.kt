package com.eco_picker.api.domain.user.repository

import com.eco_picker.api.domain.user.data.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?

    @Query("SELECT u FROM UserEntity u WHERE u.username = :username OR u.email = :email")
    fun findByUsernameOrEmail(@Param("username") username: String, @Param("email") email: String): UserEntity?
}
