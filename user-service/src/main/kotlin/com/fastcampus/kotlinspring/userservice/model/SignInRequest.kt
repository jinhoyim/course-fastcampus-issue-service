package com.fastcampus.kotlinspring.userservice.model

data class SignInRequest(
    val email: String,
    val password: String
)
