package com.awesome.network

import com.awesome.entities.Chat
import com.awesome.entities.ChatType
import com.awesome.repository.response.UserDto
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBDialogType
import com.quickblox.users.model.QBUser

@JvmName("QBUserToEntity")
fun QBUser.toEntity() = UserDto(
    id = id,
    fullName = fullName,
    email = email,
    login = login,
    phone = phone,
    website = website,
    lastRequestAt = lastRequestAt,
    externalId = externalId,
    facebookId = facebookId,
    twitterId = twitterId,
    twitterDigitsId = twitterDigitsId,
    imageId = fileId
)

@JvmName("listQBUserToEntity")
fun List<QBUser>.toEntity() : List<UserDto> = this.map { it.toEntity() }

@JvmName("dialogTypeToEntity")
fun QBDialogType.toEntity() : ChatType{
    return when(this){
        QBDialogType.PUBLIC_GROUP -> ChatType.PUBLIC_GROUP
        QBDialogType.GROUP -> ChatType.GROUP
        QBDialogType.PRIVATE -> ChatType.PRIVATE
    }
}

@JvmName("chatDialogToEntity")
fun QBChatDialog.toEntity() = Chat(
    dialogId = dialogId,
    name = name?: "",
    Image = photo ?: "",
    lastMessage = lastMessage?: "",
    lastMessageDateSent = lastMessageDateSent?: 0,
    unreadMessageCount = unreadMessageCount?: 0,
    membersIds = occupants?: emptyList(),
    type = type.toEntity()
)

@JvmName("listQBChatDialogToEntity")
fun List<QBChatDialog>.toEntity() : List<Chat> = this.map { it.toEntity() }
