package com.awesome.viewmodel.chooseMembers

import com.awesome.viewmodel.search.UserUiState

data class ChooseMemberUiState(
    val isLoading: Boolean = false,
    val error: String = "",
    val searchInput: String = "",
    val users: List<UserUiState> = emptyList(),
    val selectedUserId: Int = 0,
)
