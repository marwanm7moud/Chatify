package com.awesome.viewmodel.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.repos.ChatRepository
import com.awesome.entities.utils.UpdatedOrDeletedUserException
import com.awesome.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val chatRepository: ChatRepository,
) : BaseViewModel<HomeUiState, HomeEvents>(HomeUiState()), HomeInteractions {
    init {
        isLoggedIn()
        connectToChatServer()
        connectionState()
    }

    private fun isLoggedIn() {
        collectFlow(authRepository.getLoginState()){loginState->
            if (!loginState)sendEvent(HomeEvents.NavigateToLoginScreen)
        }
    }

    private fun connectionState() {
        collectFlow(chatRepository.subscribeToConnectionState()){connectionState->
            _state.update { it.copy(connectionState = connectionState) }
        }
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
                _state.update { it.copy(isSessionExpired = true) }
                chatRepository.disconnectFromChatServer()
            }
        }
    }
    private fun isSuccessNullReturn(unit: Unit) {}
    override fun onSessionExpiredConfirm() {
        viewModelScope.launch {
            authRepository.manageLoginState(false)
        }
        sendEvent(HomeEvents.NavigateToLoginScreen)
    }

    override fun onSearchIconClicked() {
        sendEvent(HomeEvents.NavigateToSearchScreen)
    }
}