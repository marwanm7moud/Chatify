package com.awesome.ui.navigation

sealed class ChatifyScreens(val route:String){
    object Login : ChatifyScreens("login_screen")
    object SignUp : ChatifyScreens("sign_up_screen")
}
