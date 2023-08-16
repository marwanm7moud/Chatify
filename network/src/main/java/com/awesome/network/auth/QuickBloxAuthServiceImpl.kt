package com.awesome.network.auth

import android.os.Bundle
import android.util.Log
import com.awesome.network.toUserDto
import com.awesome.repository.model.request.UserSignUpRequest
import com.awesome.repository.model.response.UserDto
import com.quickblox.auth.session.QBSession
import com.quickblox.auth.session.QBSessionManager
import com.quickblox.auth.session.QBSessionParameters
import com.quickblox.chat.QBChatService
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.XMPPConnection
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class QuickBloxAuthServiceImpl @Inject constructor() : AuthService {
    override suspend fun signUp(userSignUpRequest: UserSignUpRequest): UserDto {
        val user = QBUser()
        user.email = userSignUpRequest.email
        user.password = userSignUpRequest.password
        user.login = userSignUpRequest.username
        user.fullName = userSignUpRequest.fullName
        return suspendCoroutine { cont ->
            QBUsers.signUp(user).performAsync(object : QBEntityCallback<QBUser> {
                override fun onSuccess(user: QBUser?, bundle: Bundle?) {
                    cont.resume(user?.toUserDto()!!)
                }

                override fun onError(exception: QBResponseException?) {
                    cont.resumeWithException(exception!!)
                }
            })
        }
    }

    override suspend fun login(username: String, password: String): UserDto {
        val user = QBUser()
        user.password = password
        user.login = username
        return suspendCoroutine { cont ->
            QBUsers.signIn(user).performAsync(object : QBEntityCallback<QBUser> {
                override fun onSuccess(user: QBUser?, bundle: Bundle?) {
                    cont.resume(user?.toUserDto()!!)
                }

                override fun onError(exception: QBResponseException?) {
                    cont.resumeWithException(exception!!)
                }
            })
        }
    }

    override suspend fun logout() :Boolean {
        return suspendCoroutine {con->
            QBUsers.signOut().performAsync(object : QBEntityCallback<Void> {
                override fun onSuccess(aVoid: Void?, bundle: Bundle?) {
                    con.resume(true)
                }

                override fun onError(exception: QBResponseException?) {
                    con.resumeWithException(exception!!)
                }
            })
        }
    }
}