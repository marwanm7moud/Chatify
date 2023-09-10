package com.awesome.network.contact

import com.quickblox.chat.QBRoster
import com.quickblox.chat.listeners.QBSubscriptionListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class QuickBloxContactServiceImpl @Inject constructor(
    private val contactsRoster: QBRoster
) : QuickBloxContactService {
    override fun followListener(): Flow<Int> {
       return callbackFlow{
            contactsRoster.addSubscriptionListener() { userId ->
                trySend(userId)
            }
        }
    }


    override fun followUser(userId: Int) {
        contactsRoster.subscribe(userId)
    }

    override fun confirmUserFollow(userId: Int) {
        contactsRoster.confirmSubscription(userId)
    }

    override fun rejectUserFollow(userId: Int) {
        contactsRoster.reject(userId)
    }

    override fun removeUserFollow(userId: Int) {
        contactsRoster.unsubscribe(userId)
    }

}