package com.awesome.ui.screens.home

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.awesome.ui.screens.login.navigateToLogin
import com.awesome.viewmodel.home.HomeEvents
import com.awesome.viewmodel.home.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state by homeViewModel.state.collectAsState()
    LaunchedEffect(key1 = homeViewModel.event) {
        homeViewModel.event.collectLatest {
            when (it) {
                HomeEvents.NavigateToLoginScreen -> navController.navigateToLogin()
                else -> {}
            }
        }
    }
    if (state.error == "Session Expired") {
        AlertDialog(
            title = { Text(text = state.error!!) },
            confirmButton = {
                TextButton(onClick = homeViewModel::onSessionExpiredConfirm) {
                    Text(text = "Ok")
                }
            },
            dismissButton = {},
            onDismissRequest = { }
        )
    }
    if (state.isLogged == false) {
        LaunchedEffect(key1 = Unit ){
            navController.navigateToLogin()
        }
    }

}

@Composable
fun HomeContent() {

}

@Composable
@Preview
fun HomeScreenPreview() {

}


