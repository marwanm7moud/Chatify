package com.awesome.repository.repos.messaging

import com.awesome.entities.Chat
import com.awesome.entities.Message
import com.awesome.entities.repos.ChatRepository
import com.awesome.entities.repos.MessagingRepository
import com.awesome.repository.RemoteDataSource
import com.awesome.repository.repos.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MessagingRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : MessagingRepository {
    override fun incomingMessagesListener(): Flow<Message> {
        return remoteDataSource.incomingMessagesListener().map { it.toEntity() }
    }

    override fun incomingSystemMessagesListener(): Flow<Message> {
        return remoteDataSource.incomingSystemMessagesListener().map { it.toEntity() }
    }

    override fun sendSystemMessage(recipientId: Int) {
        remoteDataSource.sendSystemMessage(recipientId)
    }
}