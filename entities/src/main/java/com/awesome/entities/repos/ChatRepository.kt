package com.awesome.entities.repos

import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun connectToChatServer()
    fun subscribeToConnectionState():Flow<String>
    fun disconnectFromChatServer()
}