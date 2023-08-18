package com.awesome.viewmodel.signUpScreen

import com.awesome.entities.UserEntity
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.repos.model.UserSignUpRequest
import com.awesome.entities.utils.UnauthorizedException
import com.awesome.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel<SignUpUiState, SignUpEvents>(SignUpUiState()), SignUpInteractions {
    override fun onUsernameChange(username: String) {
        _state.update { it.copy(username = username) }
    }

    override fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    override fun onEmailChange(email: String) {
        _state.update { it.copy(email = email) }
    }

    override fun onFullNameChange(fullName: String) {
        _state.update { it.copy(fullName = fullName) }
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

    private fun onSignUpError(e: Throwable) {
        _state.update { it.copy(isLoading = false) }
        when (e) {
            is UnauthorizedException -> _state.update { it.copy(usernamePlaceHolder = e.message) }
            else -> sendEvent(
                SignUpEvents.ShowToastForUnexpectedError(
                    e.message ?: "UnExpectedError"
                )
            )
        }
    }

    private fun onSignUpSuccess(userEntity: UserEntity) {
        _state.update { it.copy(isLoading = false) }
        sendEvent(SignUpEvents.NavigateToHomeScreen)
    }

}