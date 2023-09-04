package com.awesome.viewmodel.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.awesome.entities.UserEntity
import com.awesome.entities.repos.SearchRepository
import com.awesome.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel<SearchUiState, SearchEvents>(SearchUiState()), SearchInteractions {


    private fun searchUserByLoginOrFullName(text: String){
        tryToExecute(
            call = { searchRepository.searchUserByLoginOrFullName(text) },
            onSuccess = ::onSearchSuccess,
            onError = {}
        )
    }

    override fun onSearchInputChanged(text: String) {
        _state.update { it.copy(isLoading = true, searchInput = text) }
        searchUserByLoginOrFullName(text)
    }


    private fun onSearchSuccess(users: List<UserEntity>) {
        _state.update { it.copy(isLoading = false, users = users.toUserUiState()) }
        Log.e("TAG", "onSearchSuccess: GG")
    }
}