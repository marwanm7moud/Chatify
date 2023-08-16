package com.awesome.repository

import com.awesome.repository.model.request.UserSignUpRequest
import com.awesome.repository.model.response.UserDto

interface RemoteDataSource {
    suspend fun signUp(
        userSignUpRequest: UserSignUpRequest
    ) : UserDto

    suspend fun login(
        username:String,
        passwrod:String
    ) : UserDto

    suspend fun logout():Boolean
}