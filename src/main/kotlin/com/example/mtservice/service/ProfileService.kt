package com.example.mtservice.service

import com.example.mtservice.dto.BalanceDto

interface ProfileService {
    fun getAllBalances(userId: Long): List<BalanceDto>
    fun addNewBalance(userId: Long, amount: Double)
}
