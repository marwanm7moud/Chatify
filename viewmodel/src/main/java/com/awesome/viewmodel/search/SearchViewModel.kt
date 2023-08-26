package com.awesome.viewmodel.search

import com.awesome.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

) : BaseViewModel<SearchUiState, SearchEvents>(SearchUiState()), SearchInteractions {

    override fun onSearchInputChanged(text: String) {
        _state.update { it.copy(searchInput = text) }
    }
}
