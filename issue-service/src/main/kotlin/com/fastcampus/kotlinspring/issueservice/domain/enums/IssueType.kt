package com.fastcampus.kotlinspring.issueservice.domain.enums

enum class IssueType {
    BUG,
    TASK;

    companion object {
        operator fun invoke(value: String): IssueType = valueOf(value.uppercase())
    }
}
