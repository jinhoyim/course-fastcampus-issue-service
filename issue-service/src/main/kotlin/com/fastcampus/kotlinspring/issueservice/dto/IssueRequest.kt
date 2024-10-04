package com.fastcampus.kotlinspring.issueservice.dto

import com.fastcampus.kotlinspring.issueservice.domain.enums.IssuePriority
import com.fastcampus.kotlinspring.issueservice.domain.enums.IssueStatus
import com.fastcampus.kotlinspring.issueservice.domain.enums.IssueType

data class IssueRequest(
    val summary: String,
    val description: String,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus
)
