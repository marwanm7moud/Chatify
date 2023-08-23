package com.awesome.network

import android.util.Log
import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.entities.utils.UpdatedOrDeletedUserException
import com.awesome.network.auth.QuickBloxAuthService
import com.awesome.repository.RemoteDataSource
import com.awesome.repository.response.UserDto
import com.awesome.entities.utils.HttpStatusCode
import com.awesome.entities.utils.NetworkException
import com.awesome.entities.utils.NullDataException
import com.awesome.entities.utils.ServerException
import com.awesome.entities.utils.UnauthorizedException
import com.awesome.entities.utils.ValidationException
import com.awesome.network.chat.QuickBloxChatService
import com.quickblox.core.exception.QBResponseException
import javax.inject.Inject

class QuickBloxDataSource @Inject constructor(
    private val authService: QuickBloxAuthService,
    private val chatRepository: QuickBloxChatService
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

    override suspend fun isLoggedIn(): Boolean {
        return wrapApi { authService.isLoggedIn()}
    }

    override suspend fun connectToChatServer() {
        return wrapApi { chatRepository.connectToChatServer()}
    }

    override suspend fun subscribeToConnectionState(): String {
        return wrapApi { chatRepository.subscribeToConnectionState()}
    }

    private suspend fun <T> wrapApi(call: suspend () -> T): T {
        return try {
            call() ?: throw NullDataException("Null")
        } catch (e: QBResponseException) {
            Log.e("TAG", "onConnectToChatError: ${e.httpStatusCode}", )

            when (e.httpStatusCode) {
                HttpStatusCode.NoInternet.code -> throw NetworkException(e.errors.toString())
                HttpStatusCode.BadRequest.code -> throw NetworkException(e.errors.toString())
                HttpStatusCode.Unauthorized.code -> throw UnauthorizedException(e.errors.toString())
                HttpStatusCode.Forbidden.code -> throw NetworkException(e.errors.toString())
                HttpStatusCode.NotFound.code -> throw NetworkException(e.errors.toString())
                HttpStatusCode.Validation.code -> throw ValidationException(e.errors)
                HttpStatusCode.TooManyRequests.code -> throw NetworkException(e.errors.toString())
                HttpStatusCode.InternalServerError.code -> throw ServerException(e.errors.toString())
                HttpStatusCode.ServiceUnavailable.code -> throw ServerException(e.errors.toString())
                HttpStatusCode.DeletedUser.code -> throw UpdatedOrDeletedUserException(e.errors.toString())
            }
            throw Exception(e.message)
        } catch (e:Throwable){
            throw Exception(e.message)
        }
    }
}