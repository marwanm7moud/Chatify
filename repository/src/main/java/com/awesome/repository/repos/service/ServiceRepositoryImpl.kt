package com.awesome.repository.repos.service

import com.awesome.entities.repos.ServiceRepository
import com.awesome.repository.LocalDataSource
import com.awesome.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ServiceRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ServiceRepository {
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