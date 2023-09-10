package com.awesome.network.auth

import com.awesome.repository.response.UserDto
import java.io.InputStream

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
    suspend fun destroySession()
    suspend fun getUserImage(userAvatarId:Int): InputStream?

}