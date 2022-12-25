package com.example.mtservice.service

import com.example.mtservice.entity.BalanceEntity
import com.example.mtservice.repository.BalanceRepository
import com.example.mtservice.service.impl.BalanceServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.doReturn
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.util.*

@WebMvcTest(BalanceServiceImpl::class)
class BalanceServiceImplTest {

    @MockBean
    lateinit var balanceRepository: BalanceRepository


    private lateinit var balanceService: BalanceService

    @BeforeEach
    fun setUp() {
        balanceService = BalanceServiceImpl(balanceRepository)
    }

    @Test
    fun shouldErrorWhenBalanceNotFound() {
        val empty: Optional<BalanceEntity> = Optional.empty()
        doReturn(empty).`when`(balanceRepository).findById(anyLong())

        try {
            balanceService.add(1L, 1)
        } catch (e: ResponseStatusException) {
            assertEquals(e.statusCode, HttpStatus.NOT_FOUND)
        }
    }
}
