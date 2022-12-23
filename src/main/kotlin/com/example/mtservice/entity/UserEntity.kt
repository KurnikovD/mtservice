package com.example.mtservice.entity

import jakarta.persistence.*

@Entity(name = "users")
class UserEntity {

    @Id
    @GeneratedValue
    var id: Long? = null

    lateinit var username: String

    lateinit var password: String

    constructor()

    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }
}
