package com.awesome.network.chat

interface QuickBloxChatService {
    suspend fun connectToChatServer()
    suspend fun subscribeToConnectionState():String
}