package com.example.mtservice.service.impl

import com.example.mtservice.service.BalanceService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.EnableCaching
import org.springframework.stereotype.Service

@Service
@EnableCaching
class CacheDecorator(
    @Qualifier("queueDecorator")
    private val target: BalanceService,
): BalanceService {

    @CacheEvict(key = "#id", cacheNames = ["getBalance"])
    override fun add(id: Long, amount: Long) {
        target.add(id, amount)
    }

    @Cacheable(key = "#id", cacheNames = ["getBalance"])
    override fun get(id: Long): Long {
        return target.get(id)
    }
}
