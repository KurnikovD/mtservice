package com.example.mtservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "users")
class UserEntity {
    @Id
    var id: Long? = null

    lateinit var username: String

    lateinit var password: String

    var balance: Double = 0.0

    constructor()

    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }
}
