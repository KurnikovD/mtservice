package com.example.mtservice.entity

import jakarta.persistence.*

@Entity(name = "balance")
class BalanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null

    var balance: Double = 0.0

    var userId: Long? = null

    constructor()

    constructor(userId: Long, balance: Double) {
        this.balance = balance
        this.userId = userId
    }
}
