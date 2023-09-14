package com.awesome.entities.repos

import com.awesome.entities.Chat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun createPrivateChat(secondUserId:Int)
    suspend fun createGroupChat(
        chatName:String,
        chatPhoto:String?,
        membersId:ArrayList<Int>,
    )
    fun getAllChats(): Flow<List<Chat>>

}