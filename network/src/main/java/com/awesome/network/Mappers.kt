package com.awesome.network

import com.awesome.entities.Chat
import com.awesome.entities.ChatType
import com.awesome.repository.response.UserDto
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBDialogType
import com.quickblox.users.model.QBUser

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
    twitterDigitsId = twitterDigitsId
)

fun List<QBUser>.toEntity() : List<UserDto> = this.map { it.toEntity() }

fun QBDialogType.toEntity() : ChatType{
    return when(this){
        QBDialogType.PUBLIC_GROUP -> ChatType.PUBLIC_GROUP
        QBDialogType.GROUP -> ChatType.GROUP
        QBDialogType.PRIVATE -> ChatType.PRIVATE
    }
}

fun QBChatDialog.toEntity() = Chat(
    dialogId = dialogId,
    name = name,
    Image = photo,
    lastMessage = lastMessage,
    lastMessageDateSent = lastMessageDateSent,
    unreadMessageCount = unreadMessageCount,
    membersIds = occupants,
    type = type.toEntity()
)
fun List<QBChatDialog>.toEntity() : List<Chat> = this.map { it.toEntity() }
