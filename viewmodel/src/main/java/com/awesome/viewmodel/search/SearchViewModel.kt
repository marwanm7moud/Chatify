package com.awesome.viewmodel.search

import android.util.Log
import com.awesome.entities.User
import com.awesome.entities.repos.AuthRepository
import com.awesome.entities.repos.SearchRepository
import com.awesome.usecase.search.SearchUserByUsernameUseCase
import com.awesome.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUserByUsernameUseCase: SearchUserByUsernameUseCase
) : BaseViewModel<SearchUiState, SearchEvents>(SearchUiState()), SearchInteractions {


    private fun searchUserByLoginOrFullName(text: String) {
        tryToExecute(
            call = { searchUserByUsernameUseCase(text) },
            onSuccess = ::onSearchSuccess,
            onError = {}
        )
    }

    override fun onSearchInputChanged(text: String) {
        _state.update { it.copy(isLoading = true, searchInput = text) }
        searchUserByLoginOrFullName(text)
    }


    private fun onSearchSuccess(users: List<User>) {
        _state.update {
            it.copy(
                isLoading = false,
                users = users.toUserUiState()
            )
        }
    }
}