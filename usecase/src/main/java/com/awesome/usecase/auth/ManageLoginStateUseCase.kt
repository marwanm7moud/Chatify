package com.awesome.usecase.auth

import com.awesome.entities.User
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.repos.model.UserSignUpRequest
import javax.inject.Inject

class ManageLoginStateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun setLoginState(isLogin: Boolean) = authRepository.manageLoginState(isLogin)
    fun getLoginState() = authRepository.getLoginState()
}