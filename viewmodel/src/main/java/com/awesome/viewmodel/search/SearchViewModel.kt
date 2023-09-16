package com.awesome.viewmodel.search

import com.awesome.entities.User
import com.awesome.usecase.search.LoadUsersWithoutQueryUseCase
import com.awesome.usecase.search.SearchUserByFullNameUseCase
import com.awesome.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUserByFullnameUseCase: SearchUserByFullNameUseCase,
    private val loadUsersWithoutQueryUseCase: LoadUsersWithoutQueryUseCase,
) : BaseViewModel<SearchUiState, SearchEvents>(SearchUiState()), SearchInteractions {

    init {
        loadUsersWithoutQuery()
    }

    private fun loadUsersWithoutQuery() {
        tryToExecute(call = { loadUsersWithoutQueryUseCase() },
            onSuccess = ::onSuccess,
            onError = {})
    }

    private fun searchUserByLoginOrFullName(text: String) {
        tryToExecute(call = { searchUserByFullnameUseCase(text) },
            onSuccess = ::onSuccess,
            onError = {})
    }

    override fun onSearchInputChanged(text: String) {
        _state.update { it.copy(isLoading = true, searchInput = text) }
        searchUserByLoginOrFullName(text)
    }


    private fun onSuccess(users: List<User>) {
        _state.update {
            it.copy(
                isLoading = false, users = users.toUserUiState()
            )
        }
    }
}