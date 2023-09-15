package com.awesome.network.messaging

import android.os.Bundle
import android.util.Log
import com.awesome.entities.Chat
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
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

const val PROPERTY_OCCUPANTS_IDS = "current_occupant_ids"
const val PROPERTY_DIALOG_TYPE = "type"
const val PROPERTY_DIALOG_NAME = "room_name"
const val PROPERTY_NOTIFICATION_TYPE = "notification_type"
const val CREATING_DIALOG = "1"
const val OCCUPANTS_ADDED = "2"
const val OCCUPANT_LEFT = "3"
const val PROPERTY_NEW_OCCUPANTS_IDS = "new_occupants_ids"

class QuickBloxMessagingServiceImpl @Inject constructor(
    private val quickBloxService: QBChatService,
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
        chatMessage.setProperty("custom_property_1", "custom_value_1")
        chatMessage.setProperty("custom_property_2", "custom_value_2")
        chatMessage.setProperty("custom_property_3", "custom_value_3")
        systemMessagesManager.sendSystemMessage(chatMessage)
    }

    private fun buildMessageCreatedGroupDialog(dialog: QBChatDialog): QBChatMessage {
        val qbChatMessage = QBChatMessage()
        qbChatMessage.dialogId = dialog.dialogId
        qbChatMessage.setProperty(
            PROPERTY_OCCUPANTS_IDS,
            getOccupantsIdsStringFromList(dialog.occupants)
        )
        qbChatMessage.setProperty(PROPERTY_DIALOG_TYPE, dialog.type.code.toString())
        qbChatMessage.setProperty(PROPERTY_DIALOG_NAME, dialog.name.toString())
        qbChatMessage.setProperty(PROPERTY_NOTIFICATION_TYPE, CREATING_DIALOG)
        qbChatMessage.dateSent = System.currentTimeMillis() / 1000
        qbChatMessage.body = quickBloxService.user.fullName + "created the group chat" + dialog.name
        qbChatMessage.setSaveToHistory(true)
        qbChatMessage.isMarkable = true
        return qbChatMessage
    }
}