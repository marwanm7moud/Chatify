package com.awesome.entities.repos

import com.awesome.entities.User

interface SearchRepository {
    suspend fun searchUserByFullName(searchValue: String): List<User>
    suspend fun loadUsersWithoutQuery() : List<User>

}