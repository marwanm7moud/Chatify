package com.awesome.viewmodel.chooseMembers

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.awesome.entities.User
import com.awesome.entities.repos.ChatRepository
import com.awesome.entities.repos.SearchRepository
import com.awesome.usecase.chat.CreatePrivateChatUseCase
import com.awesome.usecase.messaging.GetIncomingSystemMessages
import com.awesome.usecase.messaging.SendSystemMessage
import com.awesome.usecase.search.SearchUserByUsernameUseCase
import com.awesome.viewmodel.BaseViewModel
import com.awesome.viewmodel.search.UserUiState
import com.awesome.viewmodel.search.toUserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseMemberViewModel @Inject constructor(
    private val searchUserByUsernameUseCase: SearchUserByUsernameUseCase,
    private val createPrivateChatUseCase: CreatePrivateChatUseCase,
    private val sendSystemMessage: SendSystemMessage,
) : BaseViewModel<ChooseMemberUiState , ChooseMemberEvents>(ChooseMemberUiState()) , ChooseMemberInteraction {

    private fun searchUserByLoginOrFullName(text: String){
        tryToExecute(
            call = { searchUserByUsernameUseCase(text) },
            onSuccess = ::onSearchSuccess,
            onError = {}
        )
    }
    private fun onSearchSuccess(users: List<User>) {
        _state.update { it.copy(
            isLoading = false,
            users = users.toUserUiState()
        ) }
    }

    override fun onSearchInputChanged(input: String) {
        _state.update { it.copy(isLoading = true, searchInput = input) }
        searchUserByLoginOrFullName(input)
    }

    override fun onClickUser(userUiState: UserUiState) {
        _state.update { it.copy(selectedUser = userUiState) }
    }
    private fun createPrivateChat(){
        tryToExecute(
            call = { createPrivateChatUseCase(state.value.selectedUser.id)},
            onSuccess = {
                sendSystemMessage(state.value.selectedUser.id )
                sendEvent(ChooseMemberEvents.NavigateBackWithNewChat)
            },
            onError = {
                Log.e("TAG", "createPrivateChat: ${it.message}", )
            }
        )
    }

    override fun onClickDone() {
        createPrivateChat()
    }
}