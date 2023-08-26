package com.awesome.viewmodel.login

interface LoginInteractions {
    fun onUsernameChange(username:String)
    fun onPasswordChange(password:String)
    fun onCLickLogin()
    fun onNavigateToSignUp()
}