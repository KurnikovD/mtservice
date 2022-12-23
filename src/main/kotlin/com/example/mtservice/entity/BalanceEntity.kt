package com.example.mtservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "balance")
class BalanceEntity {
    @Id
    var id: Long? = null

    var balance: Double = 0.0

    var userId: Long? = null

    constructor()

    constructor(userId: Long, balance: Double) {
        this.balance = balance
        this.userId = userId
    }
}
