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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class QuickBloxMessagingServiceImpl @Inject constructor(
    private val qbChatService: QBChatService,
) : QuickBloxMessagingService {

    private lateinit var systemMessagesListener: QBSystemMessageListener
    private lateinit var systemMessagesManager: QBSystemMessagesManager
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

    override fun incomingSystemMessagesListener(): Flow<MessageDto> = callbackFlow {
        try{
            systemMessagesListener = object : QBSystemMessageListener {
                override fun processMessage(chatMessage: QBChatMessage) {
                    Log.e("TAG", "processMessage: $chatMessage")
                    trySend(chatMessage.toEntity())
                }

                override fun processError(exception: QBChatException, chatMessage: QBChatMessage) {
                    Log.e("TAG", "error: $chatMessage")
                }
            }
            systemMessagesManager = qbChatService.systemMessagesManager
            systemMessagesManager.addSystemMessageListener(systemMessagesListener)
        }catch (e:Exception){
            Log.e("TAG", "error: gg")
            qbChatService.login(qbChatService.user)
        }
        awaitClose { systemMessagesManager.removeSystemMessageListener(systemMessagesListener) }
    }

    override fun sendSystemMessage(chatId: String, recipientId: Int) {
        val chatMessage = QBChatMessage()
        chatMessage.dialogId = chatId
        chatMessage.recipientId = recipientId
        try {
            systemMessagesManager.sendSystemMessage(chatMessage)
        }catch (e:Exception){
            Log.d("TAG", "Sending System Message Error: " + e.message)
        }
    }
}