package com.awesome.entities.repos

import com.awesome.entities.Message
import kotlinx.coroutines.flow.Flow

interface MessagingRepository {
    fun incomingMessagesListener() : Flow<Message>
    fun incomingSystemMessagesListener() : Flow<Message>

    fun sendSystemMessage(recipientId :Int)
}