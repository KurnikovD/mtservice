package com.example.mtservice.service

interface BalanceService {
    fun add(id: Long, amount: Double)
    fun get(id: Long): Double
}
