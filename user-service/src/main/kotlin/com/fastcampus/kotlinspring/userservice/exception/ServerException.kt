package com.fastcampus.kotlinspring.userservice.exception

sealed class ServerException(
    val code: Int,
    override val message: String,
) : RuntimeException(message)

data class UserExistsException(
    override val message: String = "이미 존재하는 사용자입니다.",
) : ServerException(409, message)

data class UserNotFoundException(
    override val message: String = "사용자를 찾을 수 없습니다.",
) : ServerException(404, "사용자를 찾을 수 없습니다.")

data class PasswordNotMatchedException(
    override val message: String = "비밀번호가 일치하지 않습니다.",
) : ServerException(400, message)