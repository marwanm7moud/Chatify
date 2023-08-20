package com.awesome.ui.screens.signup

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.awesome.ui.navigation.ChatifyScreens

fun NavGraphBuilder.signUpRoute(navController: NavController){
    composable(ChatifyScreens.SignUp.route){
        SignUpScreen(navController)
    }
}

fun NavController.navigateToSignUp(){
    popBackStack()
    navigate(ChatifyScreens.SignUp.route)
}