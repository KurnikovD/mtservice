package com.example.mtservice.service.impl

import com.example.mtservice.repository.BalanceRepository
import com.example.mtservice.service.BalanceService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BalanceServiceImpl(private val balanceRepository: BalanceRepository) : BalanceService {

    @Transactional
    override fun add(id: Long, amount: Long) {
        balanceRepository.findById(id)
            .ifPresentOrElse({
                it.balance += amount
                balanceRepository.save(it)
            }, {
                error("Balance not found")
            })
    }

    override fun get(id: Long): Long {
        return balanceRepository.findById(id)
            .map { it.balance }
            .orElseThrow { error("Balance not found") }
    }
}
