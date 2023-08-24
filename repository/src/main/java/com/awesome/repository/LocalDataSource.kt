package com.awesome.repository

import kotlinx.coroutines.flow.Flow


interface LocalDataSource {
    suspend fun setLoginStatus(isLogged: Boolean)

    suspend fun getLoginState(): Flow<Boolean>

}