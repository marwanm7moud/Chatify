package com.awesome.network.auth

import android.os.Bundle
import com.awesome.network.toEntity
import com.awesome.repository.response.UserDto
import com.quickblox.auth.QBAuth
import com.quickblox.auth.session.QBSessionManager
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class QuickBloxQuickBloxAuthServiceImpl @Inject constructor() : QuickBloxAuthService {
    override suspend fun signUp(userSignUpRequest: com.awesome.entities.repos.model.UserSignUpRequest): UserDto {
        val user = QBUser()
        user.email = userSignUpRequest.email
        user.password = userSignUpRequest.password
        user.login = userSignUpRequest.username
        user.fullName = userSignUpRequest.fullName
        return suspendCoroutine { cont ->
            QBUsers.signUp(user).performAsync(object : QBEntityCallback<QBUser> {
                override fun onSuccess(user: QBUser?, bundle: Bundle?) {
                    cont.resume(user?.toEntity()!!)
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
                    cont.resume(user?.toEntity()!!)
                }

                override fun onError(exception: QBResponseException?) {
                    cont.resumeWithException(exception!!)
                }
            })
        }
    }

    override suspend fun logout(): Boolean {
        return suspendCoroutine { con ->
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

    override suspend fun isLoggedIn(): Boolean {
        return QBSessionManager.getInstance().sessionParameters != null
    }

    override suspend fun destroySession() {
        return suspendCoroutine { con ->
            QBAuth.deleteSession().performAsync(object : QBEntityCallback<Void> {
                override fun onSuccess(aVoid: Void?, bundle: Bundle?) {
                    con.resume(Unit)
                }

                override fun onError(exception: QBResponseException?) {
                    con.resumeWithException(exception!!)
                }
            })
        }
    }
}