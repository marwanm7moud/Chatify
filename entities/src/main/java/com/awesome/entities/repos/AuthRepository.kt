package com.awesome.entities.repos

import com.awesome.entities.UserEntity
import com.awesome.entities.repos.model.UserSignUpRequest

interface AuthRepository {
    suspend fun signUp(
        userSignUpRequest: UserSignUpRequest
    ) : UserEntity

    suspend fun login(
        username:String,
        passwrod:String
    ) : UserEntity

    suspend fun logout():Boolean
}