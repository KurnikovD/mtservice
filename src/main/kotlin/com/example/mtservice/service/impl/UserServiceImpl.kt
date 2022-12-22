package com.example.mtservice.service.impl

import com.example.mtservice.dto.UserDto
import com.example.mtservice.entity.UserEntity
import com.example.mtservice.repository.UserRepository
import com.example.mtservice.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    override fun signup(userDto: UserDto) {
        val user = UserEntity(
            userDto.username,
            userDto.password
        )
        userRepository.save(user)
    }
}
