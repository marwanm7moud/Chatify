package com.awesome.network.chat

import android.os.Bundle
import android.util.Log
import com.quickblox.auth.session.QBSessionParameters
import com.quickblox.chat.QBChatService
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.model.QBUser
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.XMPPConnection
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class QuickBloxChatServiceImpl @Inject constructor(
    private val sessionParameters : QBSessionParameters?,
    private val qbChatService: QBChatService
) : QuickBloxChatService {
    override suspend fun connectToChatServer() {
        val user = QBUser()
        user.id = sessionParameters?.userId
        user.password = sessionParameters?.userPassword
        return suspendCoroutine{cont->
            qbChatService.login(user, object : QBEntityCallback<Void> {
            override fun onSuccess(aVoid: Void?, bundle: Bundle?) {}

            override fun onError(exception: QBResponseException?) {
                cont.resumeWithException(exception!!)
            }
        })
        }
    }

    override suspend fun subscribeToConnectionState() : String {
        return suspendCoroutine {cont->
            val connectionListener = object : ConnectionListener {
                override fun connected(connection: XMPPConnection) {
                    Log.e("TAG", "connected: ", )
                }

                override fun authenticated(connection: XMPPConnection, resumed: Boolean) {
                    Log.e("TAG", "authenticated: ${connection.isAuthenticated} ", )
                }

                override fun connectionClosed() {
                    Log.e("TAG", "connectionClosed: ", )
                }

                override fun connectionClosedOnError(exception: Exception) {
                    Log.e("TAG", "connectionClosedOnError: ", )
                }

                override fun reconnectionSuccessful() {
                    Log.e("TAG", "reconnectionSuccessful: ", )
                }

                override fun reconnectingIn(seconds: Int) {
                    Log.e("TAG", "reconnectingIn: ", )
                }

                override fun reconnectionFailed(exception: Exception) {
                    Log.e("TAG", "reconnectionFailed: ", )
                }
            }
            qbChatService.addConnectionListener(connectionListener)
        }
    }
}