package com.awesome.entities.repos

import com.awesome.entities.User

interface SearchRepository {
    suspend fun searchUserByLoginOrFullName(searchValue: String): List<User>
}