package com.awesome.entities.repos

interface ChatRepository {
    suspend fun connectToChatServer()
    suspend fun subscribeToConnectionState():String
}