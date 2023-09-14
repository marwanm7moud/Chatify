package com.awesome.network.service

import kotlinx.coroutines.flow.Flow

interface QuickBloxService {
    suspend fun connectToChatServer()
    fun subscribeToConnectionState(): Flow<String>
    fun disconnectFromChatServer()
}