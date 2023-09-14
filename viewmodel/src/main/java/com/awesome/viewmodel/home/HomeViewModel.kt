package com.awesome.viewmodel.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.awesome.entities.utils.UpdatedOrDeletedUserException
import com.awesome.usecase.auth.ManageLoginStateUseCase
import com.awesome.usecase.chat.GetAllChatsUseCase
import com.awesome.usecase.service.ManageChatServerConnectionUseCase
import com.awesome.usecase.service.GetConnectionStateUseCase
import com.awesome.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val manageLoginStateUseCase: ManageLoginStateUseCase,
    private val getAllChatsUseCase: GetAllChatsUseCase,
    private val getConnectionStateUseCase: GetConnectionStateUseCase,
    private val manageChatServerConnectionUseCase: ManageChatServerConnectionUseCase
) : BaseViewModel<HomeUiState, HomeEvents>(HomeUiState()), HomeInteractions {
    init {
        isLoggedIn()
        connectToChatServer()
        connectionState()
        getAllChats()
    }

    private fun getAllChats(){
        viewModelScope.launch {
            getAllChatsUseCase().collectLatest {chats->
                _state.update { it.copy(chats = chats.map { it.toState() } ) }
                Log.e("TAG", "getAllChats: $chats", )
            }
        }
    }

    private fun isLoggedIn() {
        collectFlow(manageLoginStateUseCase.getLoginState()) { loginState ->
            if (!loginState) {
                sendEvent(HomeEvents.NavigateToLoginScreen)
            }
            else {

            }
        }
    }

    private fun connectionState() {
        collectFlow(getConnectionStateUseCase()) { connectionState ->
            _state.update { it.copy(connectionState = connectionState) }
        }
    }

    private fun connectToChatServer() {
        tryToExecute(
            call = manageChatServerConnectionUseCase::connectToChatServer,
            onSuccess = ::isSuccessNullReturn,
            onError = ::onConnectToChatError
        )
    }

    private fun onConnectToChatError(throwable: Throwable) {
        when (throwable) {
            is UpdatedOrDeletedUserException -> {
                _state.update { it.copy(isSessionExpired = true) }
                manageChatServerConnectionUseCase.disconnectToChatServer()
            }
        }
    }

    private fun isSuccessNullReturn(unit: Unit) {}
    override fun onSessionExpiredConfirm() {
        viewModelScope.launch {
            manageLoginStateUseCase.setLoginState(false)
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