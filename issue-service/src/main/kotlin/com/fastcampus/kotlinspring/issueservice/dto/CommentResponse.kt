package com.fastcampus.kotlinspring.issueservice.dto

import com.fastcampus.kotlinspring.issueservice.domain.Comment

data class CommentResponse(
    val id: Long,
    val issueId: Long,
    val userId: Long,
    val body: String,
    val username: String
)

fun Comment.toResponse() = CommentResponse(
    id = id!!,
    issueId = issue.id!!,
    userId = userId,
    body = body,
    username = username,
)