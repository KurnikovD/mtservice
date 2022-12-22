package com.example.mtservice.service

import com.example.mtservice.dto.UserDto

interface UserService {
    fun signup(userDto: UserDto)
}
