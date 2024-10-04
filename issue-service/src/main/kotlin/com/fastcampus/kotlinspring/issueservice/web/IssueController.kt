package com.fastcampus.kotlinspring.issueservice.web

import com.fastcampus.kotlinspring.issueservice.config.AuthUser
import com.fastcampus.kotlinspring.issueservice.dto.IssueRequest
import com.fastcampus.kotlinspring.issueservice.service.IssueService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/issues")
class IssueController(
    private val issueService: IssueService,
) {
    @PostMapping
    fun create(
        authUser: AuthUser,
        @RequestBody request: IssueRequest,
    ) = issueService.create(authUser.userId, request)
}