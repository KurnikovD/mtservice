package com.example.mtservice.service

interface BalanceService {
    fun add(id: Long, amount: Long)
    fun get(id: Long): Long
}
