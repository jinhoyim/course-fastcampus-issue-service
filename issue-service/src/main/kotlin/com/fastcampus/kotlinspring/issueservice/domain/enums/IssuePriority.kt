package com.fastcampus.kotlinspring.issueservice.domain.enums

enum class IssuePriority {
    LOW,
    MEDIUM,
    HIGH;

    companion object {
        operator fun invoke(value: String): IssuePriority = valueOf(value.uppercase())
    }
}