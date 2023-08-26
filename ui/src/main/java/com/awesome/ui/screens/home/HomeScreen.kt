package com.awesome.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.awesome.ui.R
import com.awesome.ui.screens.login.navigateToLogin
import com.awesome.ui.screens.search.navigateToSearch
import com.awesome.viewmodel.home.HomeEvents
import com.awesome.viewmodel.home.HomeInteractions
import com.awesome.viewmodel.home.HomeUiState
import com.awesome.viewmodel.home.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state by homeViewModel.state.collectAsState()
    HomeContent(state, homeViewModel)
    LaunchedEffect(key1 = homeViewModel.event) {
        homeViewModel.event.collectLatest {
            when (it) {
                HomeEvents.NavigateToLoginScreen -> navController.navigateToLogin()
                HomeEvents.NavigateToSearchScreen -> navController.navigateToSearch()
                else -> {}
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(state: HomeUiState, interactions: HomeInteractions) {

    AnimatedVisibility(state.isSessionExpired) {
        AlertDialog(
            title = { Text(text = "Session Expired") },
            confirmButton = {
                TextButton(onClick = interactions::onSessionExpiredConfirm) {
                    Text(text = "Ok")
                }
            },
            onDismissRequest = { }
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = state.connectionState) },
                actions = {
                    IconButton(onClick = interactions::onSearchIconClicked) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) {

    }

}

@Composable
@Preview
fun HomeScreenPreview() {
    val nav = rememberNavController()
    HomeScreen(nav)
}


