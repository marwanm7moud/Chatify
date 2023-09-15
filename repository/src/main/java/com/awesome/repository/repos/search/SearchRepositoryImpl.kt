package com.awesome.repository.repos.search

import com.awesome.entities.User
import com.awesome.entities.repos.SearchRepository
import com.awesome.repository.RemoteDataSource
import com.awesome.repository.repos.toEntity
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : SearchRepository {
    override suspend fun searchUserByLoginOrFullName(searchValue: String): List<User> {
        return remoteDataSource.searchUserByLoginOrFullName(searchValue).toEntity()
    }
}