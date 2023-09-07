package com.awesome.entities.repos

import com.awesome.entities.User
import com.awesome.entities.repos.model.UserSignUpRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signUp(
        userSignUpRequest: UserSignUpRequest
    ) : User

    suspend fun login(
        username:String,
        password:String
    ) : User

    suspend fun logout():Boolean
    suspend fun destroySession()
    suspend fun manageLoginState(isLogin:Boolean)
    fun getLoginState(): Flow<Boolean>
}