package com.awesome.ui.screens.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.awesome.ui.navigation.ChatifyScreens

fun NavGraphBuilder.loginRoute(navController: NavController){
    composable(ChatifyScreens.Login.route){
        LoginScreen(navController)
    }
}

fun NavController.navigateToLogin(){
    navigate(ChatifyScreens.Login.route){
        popUpTo(ChatifyScreens.Home.route) {
            inclusive = true
        }
    }
}