package com.awesome.network.chat

interface QuickBloxChatService {
    suspend fun createPrivateChat(secondUserId:Int)
    suspend fun createGroupChat(
        chatName:String,
        chatPhoto:String?,
        membersId:ArrayList<Int>,
    )
}