package com.example.mtservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.GenericGenerator

@Entity(name = "users")
class UserEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private var id: String? = null

    lateinit var username: String

    lateinit var password: String

    constructor()

    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }
}
