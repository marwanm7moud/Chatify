package com.awesome.viewmodel.home

sealed interface HomeEvents{
    object NavigateToLoginScreen : HomeEvents
    object NavigateToSearchScreen : HomeEvents
}