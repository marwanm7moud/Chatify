package com.awesome.repository

import com.awesome.entities.Chat
import com.awesome.entities.Message
import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.repository.response.MessageDto
import com.awesome.repository.response.UserDto
import kotlinx.coroutines.flow.Flow
import java.io.InputStream

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
   suspend fun searchUserByLoginOrFullName(searchValue:String) : List<UserDto>

    suspend fun createPrivateChat(secondUserId:Int)
    suspend fun createGroupChat(
        chatName:String,
        chatPhoto:String?,
        membersId:ArrayList<Int>,
    )
     fun getAllChats(): Flow<List<Chat>>
    suspend fun getUserImage(userAvatarId:Int): InputStream?

    fun incomingMessagesListener() : Flow<MessageDto>
    fun incomingSystemMessagesListener() : Flow<MessageDto>

    fun sendSystemMessage(recipientId :Int)
}