package com.awesome.network.chat

import com.awesome.entities.Chat
import com.quickblox.chat.model.QBChatDialog
import kotlinx.coroutines.flow.Flow

interface QuickBloxChatService {
    suspend fun createPrivateChat(secondUserId:Int)
    suspend fun createGroupChat(
        chatName:String,
        chatPhoto:String?,
        membersId:ArrayList<Int>,
    )
    suspend fun getAllChats(): Flow<List<Chat>>
}