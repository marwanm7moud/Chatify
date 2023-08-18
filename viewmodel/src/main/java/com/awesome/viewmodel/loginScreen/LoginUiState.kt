package com.awesome.viewmodel.loginScreen

data class LoginUiState(
    val username: String = "",
    val usernamePlaceHolder: String? = null,
    val password: String = "",
    val passwordPlaceHolder: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
