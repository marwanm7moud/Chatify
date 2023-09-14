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
    val dialogId: String = "",
    val name: String = "marwan",
    val Image: String = "",
    val lastMessage: String ="gikfjg",
    val lastMessageDateSent: Long = 313215,
    val unreadMessageCount: Int = 2,
    val membersIds: List<Int> = emptyList(),
)

fun Chat.toState() = ChatUiState(
    dialogId,
    name,
    Image,
    lastMessage,
    lastMessageDateSent,
    unreadMessageCount,
    membersIds,
)
