package com.awesome.network.auth

import com.awesome.repository.response.UserDto

interface QuickBloxAuthService {
    suspend fun signUp(
        userSignUpRequest: com.awesome.entities.repos.model.UserSignUpRequest
    ) : UserDto

    suspend fun login(
        username:String,
        password:String
    ) : UserDto

    suspend fun logout():Boolean
    suspend fun isLoggedIn():Boolean
}