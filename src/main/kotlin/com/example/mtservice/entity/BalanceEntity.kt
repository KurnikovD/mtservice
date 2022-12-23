package com.example.mtservice.entity

import jakarta.persistence.*

@Entity(name = "balance")
class BalanceEntity {

    @Id
    @GeneratedValue
    var id: Long? = null

    var balance: Long = 0

    var userId: Long? = null

    constructor()

    constructor(userId: Long, balance: Long) {
        this.balance = balance
        this.userId = userId
    }
}
