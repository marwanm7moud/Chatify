package com.awesome.viewmodel.signUp

interface SignUpInteractions {
    fun onUsernameChange(username:String)
    fun onPasswordChange(password:String)
    fun onEmailChange(email:String)
    fun onFullNameChange(fullName:String)
    fun onCLickSignUp()
    fun onNavigateToLogin()

}