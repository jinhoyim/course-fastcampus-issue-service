package com.fastcampus.kotlinspring.issueservice.service

import com.fastcampus.kotlinspring.issueservice.domain.Comment
import com.fastcampus.kotlinspring.issueservice.domain.CommentRepository
import com.fastcampus.kotlinspring.issueservice.domain.IssueRepository
import com.fastcampus.kotlinspring.issueservice.dto.CommentRequest
import com.fastcampus.kotlinspring.issueservice.dto.CommentResponse
import com.fastcampus.kotlinspring.issueservice.dto.toResponse
import com.fastcampus.kotlinspring.issueservice.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val issueRepository: IssueRepository,
) {
    @Transactional
    fun create(issueId: Long,
               userId: Long,
               username: String,
               request: CommentRequest
    ): CommentResponse {
        val issue = issueRepository.findByIdOrNull(issueId)
            ?: throw NotFoundException("Issue(${issueId}) is not found")

        val comment = Comment(
            issue = issue,
            userId = userId,
            username = username,
            body = request.body,
        )

        issue.comments.add(comment)
        return commentRepository.save(comment).toResponse()
    }

    @Transactional
    fun edit(id: Long, userId: Long, request: CommentRequest) =
        commentRepository.findByIdAndUserId(id, userId)?.let {
            it.body = request.body
            commentRepository.save(it).toResponse()
        }

    @Transactional
    fun delete(issueId: Long, id: Long, userId: Long) {
        val issue = (issueRepository.findByIdOrNull(issueId)
            ?: throw NotFoundException("Issue(${issueId}) is not found"))
        commentRepository.findByIdAndUserId(id, userId)?.let { comment ->
            issue.comments.remove(comment)
            commentRepository.delete(comment)
        }
    }
}