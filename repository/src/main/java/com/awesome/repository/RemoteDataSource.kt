package com.awesome.repository

import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.repository.response.UserDto
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun signUp(
        userSignUpRequest: UserSignUpRequest
    ): UserDto

    suspend fun login(
        username: String,
        password: String
    ): UserDto

    suspend fun logout(): Boolean
    suspend fun isLoggedIn(): Boolean
    suspend fun destroySession()

    suspend fun connectToChatServer()
    fun subscribeToConnectionState(): Flow<String>
    fun disconnectFromChatServer()
   suspend fun searchUserByLoginOrFullName(searchValue:String) : Flow<List<UserDto>>

}