package com.fastcampus.kotlinspring.issueservice.dto

import com.fastcampus.kotlinspring.issueservice.domain.Comment
import com.fastcampus.kotlinspring.issueservice.domain.Issue
import com.fastcampus.kotlinspring.issueservice.domain.enums.IssuePriority
import com.fastcampus.kotlinspring.issueservice.domain.enums.IssueStatus
import com.fastcampus.kotlinspring.issueservice.domain.enums.IssueType
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class IssueResponse(
    val id: Long,
    val comments: List<CommentResponse> = emptyList(),
    val summary: String,
    val description: String,
    val userId: Long,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime?,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime?,
) {
    companion object {
        fun of(issue: Issue) =
            with(issue) {
                IssueResponse(
                    id = id!!,
                    comments = comments.sortedByDescending(Comment::createdAt)
                        .map(Comment::toResponse),
                    summary = summary,
                    description = description,
                    userId = userId,
                    type = type,
                    priority = priority,
                    status = status,
                    createdAt = createdAt,
                    updatedAt = updatedAt,
                )
            }
    }
}