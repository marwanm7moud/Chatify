package com.awesome.network.chat

import kotlinx.coroutines.flow.Flow

interface QuickBloxChatService {
    suspend fun connectToChatServer()
    fun subscribeToConnectionState(): Flow<String>
    fun disconnectFromChatServer()
}