package com.awesome.repository.repos.auth

import com.awesome.entities.UserEntity
import com.awesome.entities.repos.AuthRepository
import com.awesome.repository.RemoteDataSource
import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.repository.repos.toUserEntity
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : AuthRepository {
    override suspend fun signUp(userSignUpRequest: UserSignUpRequest): UserEntity {
        return remoteDataSource.signUp(userSignUpRequest).toUserEntity()
    }

    override suspend fun login(username: String, passwrod: String): UserEntity {
        return remoteDataSource.login(username , passwrod).toUserEntity()
    }

    override suspend fun logout(): Boolean {
        return remoteDataSource.logout()
    }

}