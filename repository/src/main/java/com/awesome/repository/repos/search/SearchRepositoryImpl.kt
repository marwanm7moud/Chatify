package com.awesome.repository.repos.search

import com.awesome.entities.UserEntity
import com.awesome.entities.repos.SearchRepository
import com.awesome.repository.LocalDataSource
import com.awesome.repository.RemoteDataSource
import com.awesome.repository.repos.toUserEntity
import com.awesome.repository.response.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : SearchRepository {
    override suspend fun searchUserByLoginOrFullName(searchValue: String): List<UserEntity> {
        return remoteDataSource.searchUserByLoginOrFullName(searchValue).toUserEntity()
    }
}