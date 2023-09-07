package com.awesome.network.chat

import android.os.Bundle
import com.awesome.entities.Chat
import com.awesome.network.toEntity
import com.quickblox.chat.QBRestChatService
import com.quickblox.chat.model.QBChatDialog
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


class QuickBloxChatServiceImpl @Inject constructor(

) : QuickBloxChatService {
    override suspend fun createPrivateChat(secondUserId: Int) {
        val dialog = DialogUtils.buildPrivateDialog(secondUserId)
        return suspendCoroutine { cont ->
            QBRestChatService.createChatDialog(dialog)
                .performAsync(object : QBEntityCallback<QBChatDialog> {
                    override fun onSuccess(result: QBChatDialog?, bundle: Bundle?) {
                        cont.resume(Unit)
                    }

                    override fun onError(exception: QBResponseException?) {
                        cont.resumeWithException(exception!!)
                    }
                })
        }
    }

    override suspend fun createGroupChat(
        chatName: String,
        chatPhoto: String?,
        membersId: ArrayList<Int>
    ) {
        val dialog = DialogUtils.buildDialog(chatName, QBDialogType.GROUP, membersId)
        dialog.photo = chatPhoto
        return suspendCoroutine { cont ->
            QBRestChatService.createChatDialog(dialog)
                .performAsync(object : QBEntityCallback<QBChatDialog> {
                    override fun onSuccess(result: QBChatDialog?, bundle: Bundle?) {
                        cont.resume(Unit)
                    }

                    override fun onError(exception: QBResponseException?) {
                        cont.resumeWithException(exception!!)
                    }
                })
        }
    }

    override suspend fun getAllChats(): Flow<List<Chat>> {
        val requestBuilder = QBRequestGetBuilder()
        requestBuilder.limit = 50
        requestBuilder.skip = 0
        return callbackFlow {
            QBRestChatService.getChatDialogs(null, requestBuilder)
                .performAsync(object : QBEntityCallback<java.util.ArrayList<QBChatDialog?>?> {
                    override fun onSuccess(result: ArrayList<QBChatDialog?>?, bundle: Bundle) {
                        val chats: List<Chat> =
                            result?.toList()?.mapNotNull { it?.toEntity() }.orEmpty()
                        trySend(chats)
                    }

                    override fun onError(exception: QBResponseException) {
                        //todo
                    }
                })

            awaitClose { }
        }
    }
}