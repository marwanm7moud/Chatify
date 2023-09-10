package com.awesome.network.contact

import kotlinx.coroutines.flow.Flow

interface QuickBloxContactService {

    fun followListener(): Flow<Int>
    fun followUser(userId:Int)
    fun confirmUserFollow(userId:Int)
    fun rejectUserFollow(userId:Int)
    fun removeUserFollow(userId:Int)
}