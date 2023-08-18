package com.awesome.network.auth

import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.repository.response.UserDto

interface AuthService {
    suspend fun signUp(
        userSignUpRequest: com.awesome.entities.repos.model.UserSignUpRequest
    ) : UserDto

    suspend fun login(
        username:String,
        password:String
    ) : UserDto

    suspend fun logout():Boolean
}