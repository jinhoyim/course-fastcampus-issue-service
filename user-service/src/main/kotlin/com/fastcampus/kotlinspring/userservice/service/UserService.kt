package com.fastcampus.kotlinspring.userservice.service

import com.fastcampus.kotlinspring.userservice.domain.entity.User
import com.fastcampus.kotlinspring.userservice.domain.repository.UserRepository
import com.fastcampus.kotlinspring.userservice.exception.UserExistsException
import com.fastcampus.kotlinspring.userservice.model.SignUpRequest
import com.fastcampus.kotlinspring.userservice.utils.BCryptUtils
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    suspend fun signUp(signUpRequest: SignUpRequest) {
        with(signUpRequest) {
            userRepository.findByEmail(email)?.let {
                throw UserExistsException()
            }

            val user = User(
                email = email,
                password = BCryptUtils.hash(password),
                username = username
            )
            userRepository.save(user)
        }
    }
}