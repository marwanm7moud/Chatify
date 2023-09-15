package com.awesome.entities

data class Message(
    var dialogId: String,
    val sentAtTimeStamp: Long,
    val messageContent: String,
    val readMessageUsersId: List<Int>,
    val deliveredMessageUsersId: List<Int>,
    val senderId: Int,
    val isMarkable: Boolean,
    val isDelayed: Boolean,
)
