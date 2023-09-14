package com.awesome.viewmodel.search

import com.awesome.entities.User

data class SearchUiState(
    val searchInput: String = "",
    val users: List<UserUiState> = emptyList(),
    val isLoading: Boolean = false
)

data class UserUiState(
    val fullName: String = "",
    val email: String = "",
    val id: Int = 0,
    val username: String = "",
    val image: String = "",
)

fun User.toUserUiState() = UserUiState(
    fullName = fullName,
    username = username,
    email = email,
    id = id,
    image = "" //todo we need to get images
)

fun List<User>.toUserUiState() = map { it.toUserUiState() }