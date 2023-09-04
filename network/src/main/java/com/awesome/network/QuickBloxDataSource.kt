package com.awesome.network

import android.util.Log
import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.entities.utils.UpdatedOrDeletedUserException
import com.awesome.network.auth.QuickBloxAuthService
import com.awesome.repository.RemoteDataSource
import com.awesome.repository.response.UserDto
import com.awesome.entities.utils.HttpStatusCode
import com.awesome.entities.utils.NetworkException
import com.awesome.entities.utils.NullDataException
import com.awesome.entities.utils.ServerException
import com.awesome.entities.utils.UnauthorizedException
import com.awesome.entities.utils.ValidationException
import com.awesome.network.chat.QuickBloxChatService
import com.awesome.network.search.QuickBloxSearchService
import com.quickblox.core.exception.QBResponseException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuickBloxDataSource @Inject constructor(
    private val authService: QuickBloxAuthService,
    private val chatRepository: QuickBloxChatService,
    private val searchService: QuickBloxSearchService,
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
        return wrapApi { authService.isLoggedIn()}
    }

    override suspend fun destroySession() {
        return wrapApi { authService.destroySession()}
    }

    override suspend fun connectToChatServer() {
        return wrapApi { chatRepository.connectToChatServer()}
    }

    override  fun subscribeToConnectionState(): Flow<String> {
        return chatRepository.subscribeToConnectionState()
    }

    override fun disconnectFromChatServer() {
        return chatRepository.disconnectFromChatServer()
    }

    override suspend fun searchUserByLoginOrFullName(searchValue: String): List<UserDto> {
        return searchService.searchUserByLoginOrFullName(searchValue)
    }
}