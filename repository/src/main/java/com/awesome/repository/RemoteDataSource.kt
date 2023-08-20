package com.awesome.repository

import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.repository.response.UserDto

interface RemoteDataSource {
    suspend fun signUp(
        userSignUpRequest: UserSignUpRequest
    ) : UserDto

    suspend fun login(
        username:String,
        password:String
    ) : UserDto

    suspend fun logout():Boolean
    suspend fun isLoggedIn():Boolean
}