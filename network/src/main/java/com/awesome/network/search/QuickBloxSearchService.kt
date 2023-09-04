package com.awesome.network.search

import com.awesome.repository.response.UserDto
import kotlinx.coroutines.flow.Flow

interface QuickBloxSearchService {
    suspend fun searchUserByLoginOrFullName(searchValue:String) : List<UserDto>
}