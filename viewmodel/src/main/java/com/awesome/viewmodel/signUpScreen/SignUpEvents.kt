package com.awesome.viewmodel.signUpScreen

sealed interface SignUpEvents{
    object NavigateToLoginScreen : SignUpEvents
    object NavigateToHomeScreen : SignUpEvents
    data class ShowToastForUnexpectedError(val message:String) : SignUpEvents
}
