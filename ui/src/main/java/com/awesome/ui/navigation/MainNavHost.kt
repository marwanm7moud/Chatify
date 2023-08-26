package com.awesome.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.awesome.ui.screens.home.homeRoute
import com.awesome.ui.screens.login.loginRoute
import com.awesome.ui.screens.signup.signUpRoute

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(navController = navController , startDestination = ChatifyScreens.Home.route){
        loginRoute(navController)
        signUpRoute(navController)
        homeRoute(navController)
    }
}