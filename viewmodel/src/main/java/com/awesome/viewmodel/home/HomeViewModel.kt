package com.awesome.viewmodel.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.repos.ChatRepository
import com.awesome.entities.utils.UpdatedOrDeletedUserException
import com.awesome.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val chatRepository: ChatRepository,
) : BaseViewModel<HomeUiState, HomeEvents>(HomeUiState()), HomeInteractions {
    init {
        isLoggedIn().also {
            connectToChatServer()
            connectionState()
        }
    }

    private fun isLoggedIn() {
        viewModelScope.launch {
            val isLogged = authRepository.getLoginState()
            Log.e("TAG", "isLoggedIn: $isLogged", )
            _state.update { it.copy(isLogged = isLogged) }
        }
    }

    private fun connectionState() {
        tryToExecute(
            call = chatRepository::subscribeToConnectionState,
            onSuccess = ::onConnectionStateSuccess,
            onError = ::onConnectionStateError
        )
    }

    private fun connectToChatServer() {
        tryToExecute(
            call = chatRepository::connectToChatServer,
            onSuccess = ::isSuccessNullReturn,
            onError = ::onConnectToChatError
        )
    }

    private fun onConnectToChatError(throwable: Throwable) {
        when (throwable) {
            is UpdatedOrDeletedUserException -> {
                viewModelScope.launch { authRepository.manageLoginState(false) }
                _state.update { it.copy(isSessionExpired = true) }
            }
        }
    }

    private fun onConnectionStateError(throwable: Throwable) {
        Log.e("TAG", "onConnectToChatError: ${throwable.message}")

    }

    private fun onConnectionStateSuccess(connectionState: String) {
        Log.e("TAG", "onConnectionStateSuccess: $connectionState")
    }


    private fun isSuccessNullReturn(unit: Unit) {}
    override fun onSessionExpiredConfirm() {
        sendEvent(HomeEvents.NavigateToLoginScreen)
    }
}