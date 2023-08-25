package com.awesome.repository

import kotlinx.coroutines.flow.Flow


interface LocalDataSource {
    suspend fun setLoginStatus(isLogged: Boolean)

    fun getLoginState(): Flow<Boolean>

}