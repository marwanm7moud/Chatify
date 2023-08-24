package com.awesome.network.chat

import android.os.Bundle
import android.util.Log
import com.quickblox.auth.session.QBSessionParameters
import com.quickblox.chat.QBChatService
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.model.QBUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.XMPPConnection
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class QuickBloxChatServiceImpl @Inject constructor(
    private val sessionParameters: QBSessionParameters?,
    private val qbChatService: QBChatService
) : QuickBloxChatService {
    override suspend fun connectToChatServer() {
        val user = QBUser()
        user.id = sessionParameters?.userId
        user.password = sessionParameters?.userPassword
        return suspendCoroutine { cont ->
            qbChatService.login(user, object : QBEntityCallback<Void> {
                override fun onSuccess(aVoid: Void?, bundle: Bundle?) {
                    cont.resume(Unit)
                }

                override fun onError(exception: QBResponseException?) {
                    cont.resumeWithException(exception!!)
                }
            })
        }
    }

    override fun subscribeToConnectionState(): Flow<String> = callbackFlow {
        val connectionListener = object : ConnectionListener {
            override fun connected(connection: XMPPConnection) {
                trySend("connected")
            }

            override fun authenticated(connection: XMPPConnection, resumed: Boolean) {
                trySend("authenticated")
            }

            override fun connectionClosed() {
                trySend("connectionClosed")
            }

            override fun connectionClosedOnError(exception: Exception) {
                trySend("connectionClosedOnError")
            }

            override fun reconnectionSuccessful() {
                trySend("reconnectionSuccessful")
            }

            override fun reconnectingIn(seconds: Int) {
                trySend("reconnectingIn")
            }

            override fun reconnectionFailed(exception: Exception) {
                trySend("reconnectionFailed")
            }
        }
        qbChatService.addConnectionListener(connectionListener)
        awaitClose { qbChatService.removeConnectionListener(connectionListener) }
    }

    override fun disconnectFromChatServer() {
        val isLoggedIn = qbChatService.isLoggedIn
        if (!isLoggedIn) {
            return
        }
        qbChatService.logout(object : QBEntityCallback<Void> {
            override fun onSuccess(aVoid: Void?, bundle: Bundle?) {
                qbChatService.destroy()
            }

            override fun onError(exception: QBResponseException?) {
            }
        })
    }
}