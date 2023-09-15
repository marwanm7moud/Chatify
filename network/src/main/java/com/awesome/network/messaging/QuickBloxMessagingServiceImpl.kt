package com.awesome.network.messaging

import android.util.Log
import com.awesome.network.utils.toEntity
import com.awesome.repository.response.MessageDto
import com.quickblox.chat.QBChatService
import com.quickblox.chat.QBIncomingMessagesManager
import com.quickblox.chat.QBSystemMessagesManager
import com.quickblox.chat.exception.QBChatException
import com.quickblox.chat.listeners.QBChatDialogMessageListener
import com.quickblox.chat.listeners.QBSystemMessageListener
import com.quickblox.chat.model.QBChatMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jivesoftware.smack.SmackException
import javax.inject.Inject


class QuickBloxMessagingServiceImpl @Inject constructor(
    private val qbChatService: QBChatService,
) : QuickBloxMessagingService {

    override fun incomingMessagesListener(): Flow<MessageDto> = callbackFlow {
        val incomingMessagesManager: QBIncomingMessagesManager =
            qbChatService.incomingMessagesManager

        val listener = object : QBChatDialogMessageListener {
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
        }

        incomingMessagesManager.addDialogMessageListener(listener)
        awaitClose { incomingMessagesManager.removeDialogMessageListrener(listener) }
    }
    override fun incomingSystemMessagesListener(): Flow<MessageDto> {
        return callbackFlow {
            val systemMessagesManager = QBChatService.getInstance().systemMessagesManager
            val listener = object : QBSystemMessageListener {
                override fun processMessage(chatMessage: QBChatMessage) {
                    trySend(chatMessage.toEntity())
                }

                override fun processError(
                    exception: QBChatException,
                    chatMessage: QBChatMessage
                ) {}
            }
            systemMessagesManager?.addSystemMessageListener(listener)
            awaitClose { systemMessagesManager?.removeSystemMessageListener(listener) }
        }
    }


    override fun sendSystemMessage(chatId: String, recipientId: Int) {
        val systemMessagesManager = QBChatService.getInstance().systemMessagesManager
        val chatMessage = QBChatMessage()
        chatMessage.dialogId = chatId
        chatMessage.recipientId = recipientId
        chatMessage.setSaveToHistory(false)
        chatMessage.isMarkable = false
        chatMessage.setProperty("custom_property_1", "custom_value_1")
        chatMessage.setProperty("custom_property_2", "custom_value_2")
        chatMessage.setProperty("custom_property_3", "custom_value_3")

        try {
            systemMessagesManager?.sendSystemMessage(chatMessage)
        } catch (exception: Exception) {
            Log.e("TAGA", "error: ${exception.message}")
        }
    }
}