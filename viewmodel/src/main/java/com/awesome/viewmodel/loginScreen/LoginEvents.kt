package com.awesome.viewmodel.loginScreen

sealed interface LoginEvents{
    object NavigateToSignUpScreen : LoginEvents
    object NavigateToHomeScreen : LoginEvents
    data class ShowToastForUnexpectedError(val message:String) : LoginEvents
}
