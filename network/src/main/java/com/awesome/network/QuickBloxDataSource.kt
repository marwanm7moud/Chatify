package com.awesome.network

import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.network.auth.AuthService
import com.awesome.repository.RemoteDataSource
import com.awesome.repository.response.UserDto
import com.awesome.repository.utils.HttpStatusCode
import com.awesome.repository.utils.NetworkException
import com.awesome.repository.utils.NullDataException
import com.awesome.repository.utils.ServerException
import com.awesome.repository.utils.UnauthorizedException
import com.awesome.repository.utils.ValidationException
import com.quickblox.core.exception.QBResponseException
import javax.inject.Inject

class QuickBloxDataSource @Inject constructor(
    private val authService: AuthService
) : RemoteDataSource {
    override suspend fun signUp(userSignUpRequest: UserSignUpRequest): UserDto {
        return wrapApi { authService.signUp(userSignUpRequest) }
    }

    override suspend fun login(username: String, password: String): UserDto {
        return wrapApi { authService.login(username, password) }
    }

    override suspend fun logout(): Boolean {
        return wrapApi { authService.logout() }
    }

    suspend fun <T> wrapApi(call: suspend () -> T): T {
        return try {
            call() ?: throw NullDataException("Null")
        } catch (e: QBResponseException) {
            when (e.httpStatusCode) {
                HttpStatusCode.NoInternet.code -> throw NetworkException(e.errors.toString())
                HttpStatusCode.BadRequest.code -> throw NetworkException(e.errors.toString())
                HttpStatusCode.Unauthorized.code -> throw UnauthorizedException(e.errors.toString())
                HttpStatusCode.Forbidden.code -> throw NetworkException(e.errors.toString())
                HttpStatusCode.NotFound.code -> throw NetworkException(e.errors.toString())
                HttpStatusCode.Validation.code -> throw ValidationException(e.errors.toString())
                HttpStatusCode.TooManyRequests.code -> throw NetworkException(e.errors.toString())
                HttpStatusCode.InternalServerError.code -> throw ServerException(e.errors.toString())
                HttpStatusCode.ServiceUnavailable.code -> throw ServerException(e.errors.toString())
                else -> throw Exception(e.message)
            }
        }
    }
}