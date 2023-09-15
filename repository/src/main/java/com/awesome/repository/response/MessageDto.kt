package com.awesome.repository.response

data class MessageDto(
    var dialogId: String = "",
    val sentAtTimeStamp: Long = 0L,
    val messageContent: String = "",
    val readMessageUsersId: List<Int> = emptyList(),
    val deliveredMessageUsersId: List<Int> = emptyList(),
    val senderId: Int = 0,
    val isMarkable: Boolean = false,
    val isDelayed: Boolean = false,
)
