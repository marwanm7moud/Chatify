package com.awesome.viewmodel.chooseMembers

import android.util.Log
import com.awesome.entities.User
import com.awesome.usecase.chat.CreatePrivateChatUseCase
import com.awesome.usecase.messaging.SendSystemMessage
import com.awesome.usecase.search.LoadUsersWithoutQueryUseCase
import com.awesome.usecase.search.SearchUserByFullNameUseCase
import com.awesome.viewmodel.BaseViewModel
import com.awesome.viewmodel.search.UserUiState
import com.awesome.viewmodel.search.toUserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChooseMemberViewModel @Inject constructor(
    private val searchUserByFullnameUseCase: SearchUserByFullNameUseCase,
    private val createPrivateChatUseCase: CreatePrivateChatUseCase,
    private val sendSystemMessage: SendSystemMessage,
    private val loadUsersWithoutQueryUseCase: LoadUsersWithoutQueryUseCase,
) : BaseViewModel<ChooseMemberUiState , ChooseMemberEvents>(ChooseMemberUiState()) , ChooseMemberInteraction {

    init {
        loadUsersWithoutQuery()
    }
    private fun loadUsersWithoutQuery(){
        tryToExecute(
            call = {loadUsersWithoutQueryUseCase()},
            onSuccess = ::onSuccess,
            onError = {}
        )
    }

    private fun searchUserByLoginOrFullName(text: String){
        tryToExecute(
            call = { searchUserByFullnameUseCase(text) },
            onSuccess = ::onSuccess,
            onError = {}
        )
    }
    private fun onSuccess(users: List<User>) {
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