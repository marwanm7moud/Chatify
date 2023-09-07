package com.awesome.entities

data class Chat(
    val dialogId: String,
    val name: String,
    val Image: String,
    val lastMessage: String,
    val lastMessageDateSent: Long,
    val unreadMessageCount: Int,
    val membersIds: List<Int>,
    val type: ChatType
)
