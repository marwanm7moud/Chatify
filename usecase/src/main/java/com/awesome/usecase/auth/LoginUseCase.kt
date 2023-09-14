package com.awesome.usecase.auth

import com.awesome.entities.User
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.usecase.utils.Validator
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(username: String, password: String): User {
        Validator(
            username = username,
            password = password,
        )
        return authRepository.login(username, password)

    }

}