package com.awesome.viewmodel.signUp

data class SignUpUiState(
    val username: String = "",
    val usernamePlaceHolder: String? = null,
    val password: String = "",
    val passwordPlaceHolder: String? = null,
    val email: String = "",
    val emailPlaceHolder: String? = null,
    val fullName: String = "",
    val fullNamePlaceHolder: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
