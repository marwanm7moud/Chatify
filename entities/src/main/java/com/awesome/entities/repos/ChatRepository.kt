package com.awesome.entities.repos

interface ChatRepository {
    suspend fun createPrivateChat(secondUserId:Int)
    suspend fun createGroupChat(
        chatName:String,
        chatPhoto:String?,
        membersId:ArrayList<Int>,
    )
}