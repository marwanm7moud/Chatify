package com.awesome.repository.repos.auth

import com.awesome.entities.User
import com.awesome.entities.repos.AuthRepository
import com.awesome.repository.RemoteDataSource
import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.repository.LocalDataSource
import com.awesome.repository.repos.toEntity
import kotlinx.coroutines.flow.Flow
import java.io.InputStream
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : AuthRepository {
    override suspend fun signUp(userSignUpRequest: UserSignUpRequest): User {
        return remoteDataSource.signUp(userSignUpRequest).toEntity()
    }

    override suspend fun login(username: String, password: String): User {
        return remoteDataSource.login(username, password).toEntity()
    }

    override suspend fun logout(): Boolean {
        return remoteDataSource.logout()
    }

    override suspend fun destroySession(){
        remoteDataSource.destroySession()
    }

    override suspend fun manageLoginState(isLogin: Boolean) {
        localDataSource.setLoginStatus(isLogin)
    }

    override fun getLoginState() : Flow<Boolean> {
        return localDataSource.getLoginState()
    }

    override suspend fun getUserImage(userAvatarId: Int): InputStream? {
        return remoteDataSource.getUserImage(userAvatarId)
    }
}