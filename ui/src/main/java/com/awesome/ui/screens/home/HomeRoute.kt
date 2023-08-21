package com.awesome.ui.screens.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.awesome.ui.navigation.ChatifyScreens

fun NavGraphBuilder.homeRoute(navController: NavController){
    composable(ChatifyScreens.Home.route){
        HomeScreen(navController)
    }
}

fun NavController.navigateToHome(){
    popBackStack()
    navigate(ChatifyScreens.Home.route)
}