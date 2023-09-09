package com.awesome.viewmodel.home

import com.awesome.entities.Chat
import com.awesome.entities.ChatType

data class HomeUiState(
    val isLoading: Boolean = false,
    val isLogged: Boolean = true,
    val connectionState: String = "",
    val isSessionExpired: Boolean = false,
    val chats: List<ChatUiState> = emptyList(),
    val error: String? = null
)

data class ChatUiState(
    val dialogId: String,
    val name: String,
    val Image: String,
    val lastMessage: String,
    val lastMessageDateSent: Long,
    val unreadMessageCount: Int,
    val membersIds: List<Int>,
    val type: ChatType
)

fun Chat.toState() = ChatUiState(
    dialogId,
    name,
    Image,
    lastMessage,
    lastMessageDateSent,
    unreadMessageCount,
    membersIds,
    type
)
