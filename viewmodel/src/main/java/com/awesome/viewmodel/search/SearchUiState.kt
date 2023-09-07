package com.awesome.viewmodel.search

import com.awesome.entities.User

data class SearchUiState(
    val searchInput: String = "",
    val users: List<UserUiState> = emptyList(),
    val isLoading : Boolean = false
)

data class UserUiState(
    val fullName: String = "",
    val username: String = "",
    val image: String = "",
)

fun User.toUserUiState() = UserUiState(
    fullName = fullName,
    username = username,
    image = "" //todo we need to get images
)

fun List<User>.toUserUiState() = map{it.toUserUiState()}