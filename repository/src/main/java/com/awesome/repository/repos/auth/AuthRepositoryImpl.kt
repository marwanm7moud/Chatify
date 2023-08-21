package com.awesome.repository.repos.auth

import android.util.Log
import com.awesome.entities.UserEntity
import com.awesome.entities.repos.AuthRepository
import com.awesome.repository.RemoteDataSource
import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.entities.utils.UnauthorizedException
import com.awesome.repository.LocalDataSource
import com.awesome.repository.repos.toUserEntity
import com.awesome.repository.utils.Validator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
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
        return remoteDataSource.signUp(userSignUpRequest).toUserEntity().also {
            localDataSource.setLoginStatus(true)
        }
    }

    override suspend fun login(username: String, password: String): UserEntity {
        Validator(
            username = username,
            password = password,
        )
        return remoteDataSource.login(username, password).toUserEntity().also {
            localDataSource.setLoginStatus(true)
        }
    }

    override suspend fun logout(): Boolean {
        return remoteDataSource.logout().also {
            localDataSource.setLoginStatus(false)
        }
    }

    override suspend fun isLoggedIn() : Flow<Boolean> {
        return localDataSource.currentUserLoginState
    }

}