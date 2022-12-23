package com.example.mtservice.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import lombok.Getter
import org.hibernate.annotations.GenericGenerator

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
