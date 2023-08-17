package com.awesome.viewmodel.loginScreen

import com.awesome.entities.UserEntity
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.utils.UnauthorizedException
import com.awesome.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<LoginUiState, LoginEvents>(LoginUiState()), LoginInteractions {
    override fun onUsernameChange(username: String) {
        _state.update { it.copy(username = username) }
    }

    override fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    override fun onCLickLogin() {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            call = { authRepository.login(state.value.username, state.value.password) },
            onSuccess = ::onLoginSuccess,
            onError = ::onLoginError
        )
    }

    private fun onLoginError(e: Throwable) {
        when (e) {
            is UnauthorizedException -> _state.update { it.copy(usernamePlaceHolder = e.message) }
            else -> sendEvent(
                LoginEvents.ShowToastForUnexpectedError(
                    e.message ?: "UnExpectedError"
                )
            )
        }
    }

    private fun onLoginSuccess(userEntity: UserEntity) {
        sendEvent(LoginEvents.NavigateToHomeScreen)
    }

}