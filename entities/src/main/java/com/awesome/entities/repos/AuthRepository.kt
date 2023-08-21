package com.awesome.entities.repos

import com.awesome.entities.UserEntity
import com.awesome.entities.repos.model.UserSignUpRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(
        userSignUpRequest: UserSignUpRequest
    ) : UserEntity

    suspend fun login(
        username:String,
        password:String
    ) : UserEntity

    suspend fun logout():Boolean
    suspend fun isLoggedIn(): Flow<Boolean>
}