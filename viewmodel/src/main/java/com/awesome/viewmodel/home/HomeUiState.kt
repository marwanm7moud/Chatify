package com.awesome.viewmodel.home

data class HomeUiState(
    val isLoading:Boolean = false,
    val isLogged:Boolean = true,
    val connectionState:String = "",
    val isSessionExpired:Boolean = false,
    val error:String? = null
)
