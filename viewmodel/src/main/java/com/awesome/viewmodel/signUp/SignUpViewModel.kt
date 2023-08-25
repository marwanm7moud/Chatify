package com.awesome.viewmodel.signUp

import androidx.lifecycle.viewModelScope
import com.awesome.entities.UserEntity
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.entities.utils.ValidationException
import com.awesome.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<SignUpUiState, SignUpEvents>(SignUpUiState()), SignUpInteractions {
    override fun onUsernameChange(username: String) {
        _state.update { it.copy(username = username , usernamePlaceHolder = null) }
    }

    override fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password , passwordPlaceHolder = null) }
    }

    override fun onEmailChange(email: String) {
        _state.update { it.copy(email = email , emailPlaceHolder = null) }
    }

    override fun onFullNameChange(fullName: String) {
        _state.update { it.copy(fullName = fullName , fullNamePlaceHolder = null) }
    }

    override fun onCLickSignUp() {
        _state.update { it.copy(isLoading = true) }
        val userSignUpInfo = UserSignUpRequest(
            fullName = state.value.fullName,
            email = state.value.email,
            password = state.value.password,
            username = state.value.username,
        )
        tryToExecute(
            call = { authRepository.signUp(userSignUpInfo) },
            onSuccess = ::onSignUpSuccess,
            onError = ::onSignUpError
        )
    }

    override fun onNavigateToLogin() {
        sendEvent(SignUpEvents.NavigateToLoginScreen)
    }

    private fun onSignUpError(e: Throwable) {
        _state.update { it.copy(isLoading = false) }
        when (e) {
            is ValidationException -> {
                e.messages?.forEach { errorMessage ->
                    if (errorMessage.lowercase().contains("password"))
                        _state.update { it.copy(passwordPlaceHolder = errorMessage) }
                    if (errorMessage.lowercase().contains("login") ||
                        errorMessage.lowercase().contains("username")
                    )
                        _state.update { it.copy(usernamePlaceHolder = errorMessage) }
                    if (errorMessage.lowercase().contains("email"))
                        _state.update {
                            it.copy(emailPlaceHolder = errorMessage)
                        }
                    if (errorMessage.lowercase().contains("fullname"))
                        _state.update {
                            it.copy(fullNamePlaceHolder = errorMessage)
                        }
                }
            }
            else -> sendEvent(
                SignUpEvents.ShowToastForUnexpectedError(
                    e.message ?: "UnExpectedError"
                )
            )
        }
    }

    private fun onSignUpSuccess(userEntity: UserEntity) {
        viewModelScope.launch{
            _state.update { it.copy(isLoading = false) }
            authRepository.manageLoginState(true)
            sendEvent(SignUpEvents.NavigateToHomeScreen)
        }

    }

}