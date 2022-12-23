package com.example.mtservice.service.impl

import com.example.mtservice.dto.BalanceDto
import com.example.mtservice.entity.BalanceEntity
import com.example.mtservice.repository.BalanceRepository
import com.example.mtservice.service.ProfileService
import org.springframework.stereotype.Service

@Service
class ProfileServiceImpl(private val balanceRepository: BalanceRepository): ProfileService {

    override fun getAllBalances(userId: Long): List<BalanceDto> {
        return balanceRepository.findAllByUserId(userId).map { BalanceDto(it.id!!, it.balance) }
    }

    override fun addNewBalance(userId: Long, amount: Double) {
        balanceRepository.save(BalanceEntity(userId, amount))
    }
}
