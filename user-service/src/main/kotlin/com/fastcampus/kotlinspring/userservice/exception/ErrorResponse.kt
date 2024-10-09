package com.fastcampus.kotlinspring.userservice.exception

data class ErrorResponse(
    val code: Int,
    val message: String,
)
