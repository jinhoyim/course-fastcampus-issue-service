package com.fastcampus.kotlinspring.issueservice.domain

import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {

    fun findByIdAndUserId(id: Long, userId: Long): Comment?
}