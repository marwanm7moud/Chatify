package com.awesome.network.messaging

import android.os.Bundle
import android.util.Log
import com.awesome.entities.Chat
import com.awesome.network.utils.Constants.CREATING_DIALOG
import com.awesome.network.utils.Constants.PROPERTY_DIALOG_NAME
import com.awesome.network.utils.Constants.PROPERTY_DIALOG_TYPE
import com.awesome.network.utils.Constants.PROPERTY_NOTIFICATION_TYPE
import com.awesome.network.utils.Constants.PROPERTY_OCCUPANTS_IDS
import com.awesome.network.utils.getOccupantsIdsStringFromList
import com.awesome.network.utils.toEntity
import com.awesome.repository.response.MessageDto
import com.quickblox.chat.QBChatService
import com.quickblox.chat.QBIncomingMessagesManager
import com.quickblox.chat.QBRestChatService
import com.quickblox.chat.QBSystemMessagesManager
import com.quickblox.chat.exception.QBChatException
import com.quickblox.chat.listeners.QBChatDialogMessageListener
import com.quickblox.chat.listeners.QBSystemMessageListener
import com.quickblox.chat.model.QBChatDialog
import com.quickblox.chat.model.QBChatMessage
import com.quickblox.chat.model.QBDialogType
import com.quickblox.chat.utils.DialogUtils
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.core.request.QBRequestGetBuilder
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class QuickBloxMessagingServiceImpl @Inject constructor(
    private val systemMessagesManager: QBSystemMessagesManager,
    private val incomingMessagesManager: QBIncomingMessagesManager,
) : QuickBloxMessagingService {

    private lateinit var systemMessagesListener: QBSystemMessageListener

    override fun incomingMessagesListener(): Flow<MessageDto> = callbackFlow {
        incomingMessagesManager.addDialogMessageListener(object : QBChatDialogMessageListener {
            override fun processMessage(
                dialogId: String?,
                chatMessage: QBChatMessage?,
                senderId: Int?
            ) {
                if (chatMessage != null)
                    trySend(chatMessage.toEntity())
            }

            override fun processError(
                p0: String?,
                p1: QBChatException?,
                p2: QBChatMessage?,
                p3: Int?
            ) {
            }
        })

    }

    override fun incomingSystemMessagesListener(): Flow<MessageDto> = callbackFlow {
        systemMessagesListener = object : QBSystemMessageListener {
            override fun processMessage(chatMessage: QBChatMessage) {
                trySend(chatMessage.toEntity())
            }

            override fun processError(exception: QBChatException, chatMessage: QBChatMessage) {}
        }
        systemMessagesManager.addSystemMessageListener(systemMessagesListener)
    }

    override fun sendSystemMessage(chatId: String, recipientId: Int) {
        val chatMessage = QBChatMessage()
        chatMessage.dialogId = chatId
        chatMessage.recipientId = recipientId
        systemMessagesManager.sendSystemMessage(chatMessage)
    }
}