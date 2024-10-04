package com.fastcampus.kotlinspring.issueservice.service

import com.fastcampus.kotlinspring.issueservice.domain.Issue
import com.fastcampus.kotlinspring.issueservice.domain.IssueRepository
import com.fastcampus.kotlinspring.issueservice.domain.enums.IssueStatus
import com.fastcampus.kotlinspring.issueservice.dto.IssueRequest
import com.fastcampus.kotlinspring.issueservice.dto.IssueResponse
import com.fastcampus.kotlinspring.issueservice.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IssueService(
    private val issueRepository: IssueRepository,
) {
    @Transactional
    fun create(userId: Long, request: IssueRequest): IssueResponse {
        val issue = Issue(
            userId = userId,
            summary = request.summary,
            description = request.description,
            type = request.type,
            priority = request.priority,
            status = request.status,
        )

        return IssueResponse.of(issueRepository.save(issue))
    }

    @Transactional(readOnly = true)
    fun getAll(status: IssueStatus): List<IssueResponse> {
        return issueRepository.findAllByStatusOrderByCreatedAtDesc(status)
            ?.map(IssueResponse::of)
            ?: emptyList()
    }

    @Transactional(readOnly = true)
    fun get(id: Long): IssueResponse {
        val issue = issueRepository.findByIdOrNull(id)
            ?: throw NotFoundException("이슈를 찾을 수 없습니다. id=$id")
        return IssueResponse.of(issue)
    }
}