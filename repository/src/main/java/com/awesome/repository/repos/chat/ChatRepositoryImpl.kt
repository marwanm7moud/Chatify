package com.awesome.repository.repos.chat

import com.awesome.entities.repos.ChatRepository
import com.awesome.repository.LocalDataSource
import com.awesome.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ChatRepository {
    override suspend fun connectToChatServer() {
        remoteDataSource.connectToChatServer()
    }

    override  fun subscribeToConnectionState(): Flow<String> {
       return remoteDataSource.subscribeToConnectionState()
    }

    override fun disconnectFromChatServer() {
        return remoteDataSource.disconnectFromChatServer()
    }
}