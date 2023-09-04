package com.awesome.entities.repos

import com.awesome.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchUserByLoginOrFullName(searchValue: String): List<UserEntity>
}