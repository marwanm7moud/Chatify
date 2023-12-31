package com.awesome.network

import com.awesome.entities.Chat
import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.network.auth.QuickBloxAuthService
import com.awesome.network.chat.QuickBloxChatService
import com.awesome.repository.RemoteDataSource
import com.awesome.repository.response.UserDto
import com.awesome.network.service.QuickBloxService
import com.awesome.network.search.QuickBloxSearchService
import kotlinx.coroutines.flow.Flow
import java.io.InputStream
import javax.inject.Inject

class QuickBloxDataSource @Inject constructor(
    private val authService: QuickBloxAuthService,
    private val qbService: QuickBloxService,
    private val searchService: QuickBloxSearchService,
    private val chatService: QuickBloxChatService,
) : RemoteDataSource {
    override suspend fun signUp(userSignUpRequest: UserSignUpRequest): UserDto {
        return wrapApi { authService.signUp(userSignUpRequest) }
    }

    override suspend fun login(username: String, password: String): UserDto {
        return wrapApi { authService.login(username, password) }
    }

    override suspend fun logout(): Boolean {
        return wrapApi { authService.logout() }
    }

    override suspend fun isLoggedIn(): Boolean {
        return wrapApi { authService.isLoggedIn() }
    }

    override suspend fun destroySession() {
        return wrapApi { authService.destroySession() }
    }

    override suspend fun connectToChatServer() {
        return wrapApi { qbService.connectToChatServer() }
    }

    override fun subscribeToConnectionState(): Flow<String> {
        return qbService.subscribeToConnectionState()
    }

    override fun disconnectFromChatServer() {
        return qbService.disconnectFromChatServer()
    }

    override suspend fun searchUserByLoginOrFullName(searchValue: String): List<UserDto> {
        return searchService.searchUserByLoginOrFullName(searchValue)
    }

    override suspend fun createPrivateChat(secondUserId: Int) {
        return wrapApi { chatService.createPrivateChat(secondUserId) }
    }

    override suspend fun createGroupChat(
        chatName: String,
        chatPhoto: String?,
        membersId: ArrayList<Int>
    ) {
        return wrapApi { chatService.createGroupChat(chatName, chatPhoto, membersId) }
    }

    override fun getAllChats(): Flow<List<Chat>> {
        return chatService.getAllChats()
    }

    override suspend fun getUserImage(userAvatarId: Int): InputStream? {
        return wrapApi { authService.getUserImage(userAvatarId) }
    }
}