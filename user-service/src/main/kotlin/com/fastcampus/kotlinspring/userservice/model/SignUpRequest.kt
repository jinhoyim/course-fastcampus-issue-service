package com.fastcampus.kotlinspring.userservice.model

data class SignUpRequest(
    val email: String,
    val password: String,
    val username: String,
)
