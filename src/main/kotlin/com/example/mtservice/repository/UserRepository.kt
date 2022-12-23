package com.example.mtservice.repository

import com.example.mtservice.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {

    fun findByUsername(username: String): UserEntity

}

