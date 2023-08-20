package com.awesome.repository

import kotlinx.coroutines.flow.Flow


interface LocalDataSource {
    suspend fun setUserId(userId:String)

    suspend fun getUserId(): Flow<String>

}