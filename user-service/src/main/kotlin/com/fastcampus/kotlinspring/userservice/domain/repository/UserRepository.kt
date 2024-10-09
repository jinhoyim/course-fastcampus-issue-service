package com.fastcampus.kotlinspring.userservice.domain.repository

import com.fastcampus.kotlinspring.userservice.domain.entity.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserRepository : CoroutineCrudRepository<User, Long> {
    suspend fun findByEmail(email: String): User?
}