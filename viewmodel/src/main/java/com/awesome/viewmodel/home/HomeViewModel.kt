package com.awesome.viewmodel.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.repos.ChatRepository
import com.awesome.entities.repos.ServiceRepository
import com.awesome.entities.utils.UpdatedOrDeletedUserException
import com.awesome.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val serviceRepository: ServiceRepository,
    private val chatRepository: ChatRepository
) : BaseViewModel<HomeUiState, HomeEvents>(HomeUiState()), HomeInteractions {
    init {
        isLoggedIn()
    }

    private fun getAllChats(){
        viewModelScope.launch {
            chatRepository.getAllChats().collectLatest {chats->
                _state.update { it.copy(chats = chats.map { it.toState() } ) }
                Log.e("TAG", "getAllChats: $chats", )
            }
        }
    }

    private fun isLoggedIn() {
        collectFlow(authRepository.getLoginState()) { loginState ->
            if (!loginState) {
                sendEvent(HomeEvents.NavigateToLoginScreen)
            }
            else {
                connectToChatServer().also { connectionState().also { getAllChats()} }
            }
        }
    }

    private fun connectionState() {
        collectFlow(serviceRepository.subscribeToConnectionState()) { connectionState ->
            _state.update { it.copy(connectionState = connectionState) }
        }
    }

    private fun connectToChatServer() {
        tryToExecute(
            call = serviceRepository::connectToChatServer,
            onSuccess = ::isSuccessNullReturn,
            onError = ::onConnectToChatError
        )
    }

    private fun onConnectToChatError(throwable: Throwable) {
        when (throwable) {
            is UpdatedOrDeletedUserException -> {
                _state.update { it.copy(isSessionExpired = true) }
                serviceRepository.disconnectFromChatServer()
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

    override fun onNewChatClicked() {
        sendEvent(HomeEvents.NavigateToChooseMember)
    }
}