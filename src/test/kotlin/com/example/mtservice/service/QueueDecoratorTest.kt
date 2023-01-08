package com.example.mtservice.service

import com.example.mtservice.entity.BalanceEntity
import com.example.mtservice.exception.NotEnoughMoneyException
import com.example.mtservice.repository.BalanceRepository
import com.example.mtservice.service.impl.BalanceServiceImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.doReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class QueueDecoratorTest {

    @Autowired
    lateinit var balanceRepository: BalanceRepository

    @Autowired
    @Qualifier("queueDecorator")
    lateinit var balanceService: BalanceService

    @MockBean
    lateinit var balanceServiceMock: BalanceServiceImpl

    private var balanceId: Long = 0

    @BeforeEach
    fun setUp() {
        balanceRepository.deleteAll()
        balanceId = balanceRepository.save(BalanceEntity(1, 0)).id!!
    }

    @Test
    fun queueTest() {

        runBlocking {
            (1..COUNT_OF_ITERATION).map {
                async {
                    balanceService.add(balanceId, 10)
                }
            }.awaitAll()
        }
        // TODO: I dont understand how trace up internal queue
        var count = 0
        while (balanceRepository.findById(balanceId).get().balance != 100L) {
            Thread.sleep(100)
            count++
            if (count > 1000) {
                error("Timeout")
            }
        }

        balanceRepository.findById(balanceId).ifPresent {
            assertEquals(COUNT_OF_ITERATION * AMOUNT, it.balance)
        }
    }

    @Test
    fun shouldErrorWhenAmountIsNegativeAndMoreThanBalance() {
        doReturn(100L).`when`(balanceServiceMock).get(anyLong())
        kotlin.runCatching { balanceServiceMock.add(balanceId, -101) }
            .onFailure { assertEquals(NotEnoughMoneyException::class, it::class) }
            .onSuccess { error("Should throw exception") }
    }

    companion object {
        private const val COUNT_OF_ITERATION = 10L
        private const val AMOUNT = 10L
    }
}
