package com.awesome.ui.screens.search

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.awesome.ui.navigation.ChatifyScreens

fun NavGraphBuilder.searchRoute(navController: NavController,){
    composable(ChatifyScreens.Search.route){
        SearchScreen(navController)
    }
}
fun NavController.navigateToSearch(){
    navigate(ChatifyScreens.Search.route)
}