package com.example.mtservice.repository

import com.example.mtservice.entity.BalanceEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BalanceRepository : JpaRepository<BalanceEntity, Long> {
    fun findAllByUserId(userId: Long): List<BalanceEntity>
}

