package com.fastcampus.kotlinspring.issueservice.exception

sealed class ServerException(
    val code: Int,
    override val message: String,
) : RuntimeException(message)

data class NotFoundException(
    override val message: String,
) : ServerException(404, message)

data class UnauthroizedException(
    override val message: String,
) : ServerException(401, message)

data class BadRequestException(
    override val message: String,
) : ServerException(400, message)
