package com.awesome.network.search

import android.os.Bundle
import com.awesome.network.utils.toEntity
import com.awesome.repository.response.UserDto
import com.quickblox.content.model.QBFile
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

class QuickBloxSearchServiceImpl @Inject constructor(

) : QuickBloxSearchService {
    override suspend fun searchUserByLoginOrFullName(searchValue: String): List<UserDto> = suspendCoroutine{cont->
        //todo fix search
        val searchOperator = "le"
        val typeField = "string"
        val paramFilter = "filter[]"
        val requestBuilder = QBPagedRequestBuilder()
        requestBuilder.rules.add(
            GenericQueryRule(
                paramFilter,
                "$typeField login $searchOperator $searchValue"
            )
        )

        QBUsers.getUsers(requestBuilder)
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