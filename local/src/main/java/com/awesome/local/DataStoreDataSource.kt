package com.awesome.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.awesome.repository.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class DataStoreDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : LocalDataSource {
    override suspend fun setUserId(userId: String) {
        runBlocking {
            dataStore.edit { it[USERID] = userId }
        }
    }

    override suspend fun getUserId(): Flow<String> {
        return dataStore.data.map {
            it[USERID] ?: ""
        }
    }

    companion object {
        private val USERID = stringPreferencesKey("USER_ID")
    }
}