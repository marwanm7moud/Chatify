package com.awesome.repository.repos.auth

import com.awesome.entities.UserEntity
import com.awesome.entities.repos.AuthRepository
import com.awesome.repository.RemoteDataSource
import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.repository.LocalDataSource
import com.awesome.repository.repos.toUserEntity
import com.awesome.repository.utils.Validator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : AuthRepository {
    override suspend fun signUp(userSignUpRequest: UserSignUpRequest): UserEntity {
        Validator(
            username = userSignUpRequest.username,
            password = userSignUpRequest.password,
            email = userSignUpRequest.email,
            fullName = userSignUpRequest.fullName
        )
        return remoteDataSource.signUp(userSignUpRequest).toUserEntity()
    }

    override suspend fun login(username: String, password: String): UserEntity {
        Validator(
            username = username,
            password = password,
        )
        return remoteDataSource.login(username, password).toUserEntity()
    }

    override suspend fun logout(): Boolean {
        return remoteDataSource.logout()
    }

    override suspend fun manageLoginState(isLogin: Boolean) {
        localDataSource.setLoginStatus(isLogin)
    }

    override suspend fun getLoginState() : Boolean {
        return localDataSource.getLoginState()
    }
}