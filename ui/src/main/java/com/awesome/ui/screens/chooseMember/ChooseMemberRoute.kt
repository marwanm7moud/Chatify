package com.awesome.ui.screens.chooseMember

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.awesome.ui.navigation.ChatifyScreens

fun NavGraphBuilder.chooseMemberRoute(navController: NavController){
    composable(ChatifyScreens.ChooseMember.route){
        ChooseMembers(navController)
    }
}
fun NavController.navigateToChooseMember(){
    navigate(ChatifyScreens.ChooseMember.route)
}