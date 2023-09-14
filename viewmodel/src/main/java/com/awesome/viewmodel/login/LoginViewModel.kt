package com.awesome.viewmodel.login

import androidx.lifecycle.viewModelScope
import com.awesome.entities.User
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.utils.UnauthorizedException
import com.awesome.entities.utils.ValidationException
import com.awesome.usecase.auth.LoginUseCase
import com.awesome.usecase.auth.ManageLoginStateUseCase
import com.awesome.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val manageLoginStateUseCase: ManageLoginStateUseCase
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
            call = { loginUseCase(state.value.username, state.value.password) },
            onSuccess = ::onLoginSuccess,
            onError = ::onLoginError
        )
    }

    override fun onNavigateToSignUp() {
        sendEvent(LoginEvents.NavigateToSignUpScreen)
    }

    private fun onLoginError(e: Throwable) {
        _state.update { it.copy(isLoading = false) }
        when (e) {
            is UnauthorizedException -> _state.update { it.copy(usernamePlaceHolder = e.message) }
            is ValidationException -> {
                e.messages?.forEach { errorMessage ->
                    if (errorMessage.lowercase().contains("password"))
                        _state.update { it.copy(passwordPlaceHolder = errorMessage) }
                    if (errorMessage.lowercase().contains("login") ||
                        errorMessage.lowercase().contains("username")
                    )
                        _state.update { it.copy(usernamePlaceHolder = errorMessage) }
                }
            }

            else -> sendEvent(
                LoginEvents.ShowToastForUnexpectedError(
                    e.message ?: "UnExpectedError"
                )
            )
        }
    }

    private fun onLoginSuccess(user: User) {
        viewModelScope.launch{
            _state.update { it.copy(isLoading = false) }
            manageLoginStateUseCase.setLoginState(true)
            sendEvent(LoginEvents.NavigateToHomeScreen)
        }
    }
}