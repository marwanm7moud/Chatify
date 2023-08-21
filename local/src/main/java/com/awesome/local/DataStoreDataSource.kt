package com.awesome.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.awesome.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class DataStoreDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : LocalDataSource {
    override suspend fun setLoginStatus(isLogged: Boolean) {
        dataStore.edit {
            it[booleanPreferencesKey(USERID)] = isLogged
        }
    }

    override val currentUserLoginState: Flow<Boolean>
        get() = dataStore.data.map { it[booleanPreferencesKey(USERID)] ?: false }


    companion object {
        private const val USERID = "USER_ID"
    }
}