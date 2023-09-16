package com.awesome.network.search

import com.awesome.repository.response.UserDto
import kotlinx.coroutines.flow.Flow

interface QuickBloxSearchService {
    suspend fun searchUserByFullName(searchValue:String) : List<UserDto>
    suspend fun loadUsersWithoutQuery() : List<UserDto>
}