package com.awesome.usecase.auth

import com.awesome.entities.User
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.usecase.utils.Validator
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(userSignUpRequest: UserSignUpRequest): User {
        Validator(
            username = userSignUpRequest.username,
            password = userSignUpRequest.password,
            email = userSignUpRequest.email,
            fullName = userSignUpRequest.fullName
        )
       return authRepository.signUp(userSignUpRequest).also {
           authRepository.login(userSignUpRequest.username , userSignUpRequest.password)
       }
    }

}