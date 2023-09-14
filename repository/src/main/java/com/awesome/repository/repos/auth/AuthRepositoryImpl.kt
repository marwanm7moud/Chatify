package com.awesome.repository.repos.auth

import com.awesome.entities.User
import com.awesome.entities.repos.AuthRepository
import com.awesome.repository.RemoteDataSource
import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.repository.LocalDataSource
import com.awesome.repository.repos.toUserEntity
import com.awesome.repository.utils.Validator
import kotlinx.coroutines.flow.Flow
import java.io.InputStream
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : AuthRepository {
    override suspend fun signUp(userSignUpRequest: UserSignUpRequest): User {
        Validator(
            username = userSignUpRequest.username,
            password = userSignUpRequest.password,
            email = userSignUpRequest.email,
            fullName = userSignUpRequest.fullName
        )
        return remoteDataSource.signUp(userSignUpRequest).toUserEntity()
    }

    override suspend fun login(username: String, password: String): User {
        Validator(
            username = username,
            password = password,
        )
        return remoteDataSource.login(username, password).toUserEntity()
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