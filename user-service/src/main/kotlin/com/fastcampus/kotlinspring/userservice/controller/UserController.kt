package com.fastcampus.kotlinspring.userservice.controller

import com.fastcampus.kotlinspring.userservice.model.AuthToken
import com.fastcampus.kotlinspring.userservice.model.SignInRequest
import com.fastcampus.kotlinspring.userservice.model.SignInResponse
import com.fastcampus.kotlinspring.userservice.model.SignUpRequest
import com.fastcampus.kotlinspring.userservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/signup")
    suspend fun signUp(@RequestBody request: SignUpRequest) {
        userService.signUp(request)
    }

    @PostMapping("/signin")
    suspend fun signIn(@RequestBody request: SignInRequest): SignInResponse {
        return userService.signIn(request)
    }

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun logout(@AuthToken token: String) {
        userService.logout(token)
    }
}