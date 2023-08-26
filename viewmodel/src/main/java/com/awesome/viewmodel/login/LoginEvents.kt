package com.awesome.viewmodel.login

sealed interface LoginEvents{
    object NavigateToSignUpScreen : LoginEvents
    object NavigateToHomeScreen : LoginEvents
    data class ShowToastForUnexpectedError(val message:String) : LoginEvents
}
