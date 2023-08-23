package com.awesome.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.awesome.repository.LocalDataSource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class DataStoreDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : LocalDataSource {
    override suspend fun setLoginStatus(isLogged: Boolean) {
        dataStore.edit {
            it[booleanPreferencesKey(USERID)] = isLogged
        }
    }

    override suspend fun getLoginState(): Boolean {
        return dataStore.data.map { it[booleanPreferencesKey(USERID)] ?: false }.last()
    }


    companion object {
        private const val USERID = "USER_ID"
    }
}