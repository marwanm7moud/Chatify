package com.awesome.network.auth

import com.awesome.repository.model.request.UserSignUpRequest
import com.awesome.repository.model.response.UserDto

interface AuthService {
    suspend fun signUp(
        userSignUpRequest: UserSignUpRequest
    ) : UserDto

    suspend fun login(
        username:String,
        password:String
    ) : UserDto

    suspend fun logout():Boolean
}