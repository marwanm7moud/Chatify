package com.awesome.viewmodel.login

import androidx.lifecycle.viewModelScope
import com.awesome.entities.UserEntity
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.utils.UnauthorizedException
import com.awesome.entities.utils.ValidationException
import com.awesome.viewmodel.BaseViewModel
import com.awesome.viewmodel.signUp.SignUpEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
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

    private fun onLoginSuccess(userEntity: UserEntity) {
        viewModelScope.launch{
            _state.update { it.copy(isLoading = false) }
            authRepository.manageLoginState(true)
            sendEvent(LoginEvents.NavigateToHomeScreen)
        }
    }
}