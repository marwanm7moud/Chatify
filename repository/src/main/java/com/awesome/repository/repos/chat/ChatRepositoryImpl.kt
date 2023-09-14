package com.awesome.repository.repos.chat

import com.awesome.entities.Chat
import com.awesome.entities.repos.ChatRepository
import com.awesome.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ChatRepository {
    override suspend fun createPrivateChat(secondUserId: Int) {
        remoteDataSource.createPrivateChat(secondUserId)
    }

    override suspend fun createGroupChat(
        chatName: String,
        chatPhoto: String?,
        membersId: ArrayList<Int>
    ) {
        remoteDataSource.createGroupChat(chatName, chatPhoto, membersId)
    }

    override fun getAllChats(): Flow<List<Chat>> = remoteDataSource.getAllChats()
}