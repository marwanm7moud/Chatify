package com.awesome.viewmodel.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.awesome.entities.UserEntity
import com.awesome.entities.repos.SearchRepository
import com.awesome.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel<SearchUiState, SearchEvents>(SearchUiState()), SearchInteractions {


    override fun onSearchInputChanged(text: String) {
        _state.update { it.copy(searchInput = text) }
        tryToExecute(
            call = { searchRepository.searchUserByLoginOrFullName(text) },
            onSuccess = ::onSearchSuccess,
            onError = {}
        )
    }

    private fun onSearchSuccess(flow: Flow<List<UserEntity>>) {
        viewModelScope.launch {
            flow.collectLatest {


            }
        }
    }
}
