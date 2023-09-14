package com.awesome.viewmodel.chooseMembers

import android.util.Log
import com.awesome.entities.User
import com.awesome.entities.repos.ChatRepository
import com.awesome.entities.repos.SearchRepository
import com.awesome.viewmodel.BaseViewModel
import com.awesome.viewmodel.search.UserUiState
import com.awesome.viewmodel.search.toUserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChooseMemberViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val chatRepository: ChatRepository
) : BaseViewModel<ChooseMemberUiState , ChooseMemberEvents>(ChooseMemberUiState()) , ChooseMemberInteraction {

    private fun searchUserByLoginOrFullName(text: String){
        tryToExecute(
            call = { searchRepository.searchUserByLoginOrFullName(text) },
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
            call = { chatRepository.createPrivateChat(state.value.selectedUser.id)},
            onSuccess = {
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