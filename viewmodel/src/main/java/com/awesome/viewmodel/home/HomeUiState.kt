package com.awesome.viewmodel.home

data class HomeUiState(
    val isLoading:Boolean = false,
    val isLogged:Boolean = true,
    val error:String? = null
)
