package com.awesome.repository.repos.chat

import com.awesome.entities.repos.ChatRepository
import com.awesome.repository.LocalDataSource
import com.awesome.repository.RemoteDataSource
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ChatRepository {
    override suspend fun connectToChatServer() {
        remoteDataSource.connectToChatServer()
    }

    override suspend fun subscribeToConnectionState(): String {
       return remoteDataSource.subscribeToConnectionState()
    }
}