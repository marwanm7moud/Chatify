package com.awesome.repository.repos

import com.awesome.entities.Message
import com.awesome.entities.User
import com.awesome.repository.response.MessageDto
import com.awesome.repository.response.UserDto

fun UserDto.toEntity() = User(
    id = id ?: 0,
    email = email ?: "",
    password = password ?: "",
    username = login ?: "",
    fullName = fullName ?: "",
    avatarId = imageId ?: 0
)

fun List<UserDto>.toEntity() = map { it.toEntity() }

fun MessageDto.toEntity() = Message(
    dialogId = dialogId,
    sentAtTimeStamp = sentAtTimeStamp,
    messageContent = messageContent,
    readMessageUsersId = readMessageUsersId,
    deliveredMessageUsersId = deliveredMessageUsersId,
    senderId = senderId,
    isMarkable = isMarkable,
    isDelayed = isDelayed,
)