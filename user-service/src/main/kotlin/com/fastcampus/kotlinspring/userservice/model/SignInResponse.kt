package com.fastcampus.kotlinspring.userservice.model

data class SignInResponse(
    val email: String,
    val username: String,
    val token: String,
)
