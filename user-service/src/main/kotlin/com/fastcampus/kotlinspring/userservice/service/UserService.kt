package com.fastcampus.kotlinspring.userservice.service

import com.auth0.jwt.interfaces.DecodedJWT
import com.fastcampus.kotlinspring.userservice.config.JWTProperties
import com.fastcampus.kotlinspring.userservice.domain.entity.User
import com.fastcampus.kotlinspring.userservice.domain.repository.UserRepository
import com.fastcampus.kotlinspring.userservice.exception.InvalidJwtTokenException
import com.fastcampus.kotlinspring.userservice.exception.PasswordNotMatchedException
import com.fastcampus.kotlinspring.userservice.exception.UserExistsException
import com.fastcampus.kotlinspring.userservice.exception.UserNotFoundException
import com.fastcampus.kotlinspring.userservice.model.SignInRequest
import com.fastcampus.kotlinspring.userservice.model.SignInResponse
import com.fastcampus.kotlinspring.userservice.model.SignUpRequest
import com.fastcampus.kotlinspring.userservice.utils.BCryptUtils
import com.fastcampus.kotlinspring.userservice.utils.JWTClaim
import com.fastcampus.kotlinspring.userservice.utils.JWTUtils
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties,
    private val cacheManager: CoroutineCacheManager<User>
) {
    companion object {
        private val CACHE_TTL = Duration.ofMinutes(1)
    }

    suspend fun signUp(signUpRequest: SignUpRequest) {
        with(signUpRequest) {
            userRepository.findByEmail(email)?.let {
                throw UserExistsException()
            }

            val user = User(
                email = email,
                password = BCryptUtils.hash(password),
                username = username
            )
            userRepository.save(user)
        }
    }

    suspend fun signIn(request: SignInRequest): SignInResponse {
        return with(userRepository.findByEmail(request.email) ?: throw UserNotFoundException()) {
            val verified = BCryptUtils.verify(request.password, password)
            if (!verified) {
                throw PasswordNotMatchedException()
            }

            val jwtClaim = JWTClaim(
                userId = id!!,
                email = email,
                profileUrl = profileUrl,
                username = username
            )

            val token = JWTUtils.createToken(jwtClaim, jwtProperties)

            cacheManager.awaitPut(key = token, value = this, ttl = CACHE_TTL)
            SignInResponse(
                email = email,
                username = username,
                token = token
            )
        }
    }

    suspend fun logout(token: String) {
        cacheManager.awaitEvict(token)
    }

    suspend fun getByToken(token: String): User {
        val cachedUser = cacheManager.awaitGetOrPut(key = token, ttl = CACHE_TTL) {
            val decodedJWT: DecodedJWT = JWTUtils.decode(token, jwtProperties.secret, jwtProperties.issuer)
            val userId: Long = decodedJWT.getClaim("userId").asLong() ?: throw InvalidJwtTokenException()
            get(userId)
        }
        return cachedUser
    }

    suspend fun get(userId: Long): User {
        return userRepository.findById(userId) ?: throw UserNotFoundException()
    }
}