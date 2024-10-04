package com.fastcampus.kotlinspring.issueservice.domain.enums

enum class IssueStatus {
    TODO,
    IN_PROGRESS,
    RESOLVED;

    companion object {
        operator fun invoke(value: String): IssueStatus = valueOf(value.uppercase())
    }
}