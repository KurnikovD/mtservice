package com.example.mtservice.service.impl

import com.example.mtservice.dto.UserDto
import com.example.mtservice.entity.UserEntity
import com.example.mtservice.repository.BalanceRepository
import com.example.mtservice.repository.UserRepository
import com.example.mtservice.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val balanceRepository: BalanceRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserService {
    override fun signup(userDto: UserDto) {
        val user = UserEntity(
            userDto.username,
            passwordEncoder.encode(userDto.password)

        )
        userRepository.save(user)
    }

    override fun checkUsersBalanceId(userId: Long, balanceId: Long): Boolean {
        return balanceRepository.findById(balanceId).get().userId == userId
    }
}
