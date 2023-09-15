package com.awesome.network.messaging

import com.awesome.repository.response.MessageDto
import kotlinx.coroutines.flow.Flow

interface QuickBloxMessagingService {
    fun incomingMessagesListener() : Flow<MessageDto>
    fun incomingSystemMessagesListener() : Flow<MessageDto>

    fun sendSystemMessage(chatId:String , recipientId :Int)
}