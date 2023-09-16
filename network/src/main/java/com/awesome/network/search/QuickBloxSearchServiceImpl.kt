package com.awesome.network.search

import android.os.Bundle
import com.awesome.network.utils.toEntity
import com.awesome.repository.response.UserDto
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.core.request.GenericQueryRule
import com.quickblox.core.request.QBPagedRequestBuilder
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class QuickBloxSearchServiceImpl @Inject constructor() : QuickBloxSearchService {
    override suspend fun searchUserByFullName(searchValue: String): List<UserDto> =
        suspendCoroutine { cont ->
            val requestBuilder = QBPagedRequestBuilder()
            requestBuilder.rules.add(
                GenericQueryRule("order", "desc string updated_at")
            )
            QBUsers.getUsersByFullName(searchValue, requestBuilder)
                .performAsync(object : QBEntityCallback<ArrayList<QBUser>> {
                    override fun onSuccess(usersList: ArrayList<QBUser>, bundle: Bundle) {
                        cont.resume(usersList.toEntity())
                    }

                    override fun onError(exception: QBResponseException) {
                        cont.resumeWithException(exception)
                    }
                })
        }
}