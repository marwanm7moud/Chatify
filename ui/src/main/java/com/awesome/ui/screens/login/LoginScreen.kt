package com.awesome.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.awesome.ui.screens.home.navigateToHome
import com.awesome.ui.screens.signup.navigateToSignUp
import com.awesome.ui.ui.theme.ChatifyTheme
import com.awesome.viewmodel.login.LoginEvents
import com.awesome.viewmodel.login.LoginInteractions
import com.awesome.viewmodel.login.LoginUiState
import com.awesome.viewmodel.login.LoginViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
    ) {
    val state by loginViewModel.state.collectAsState()
    LaunchedEffect(key1 = loginViewModel.event) {
        loginViewModel.event.collectLatest {
            when (it) {
                LoginEvents.NavigateToHomeScreen -> navController.navigateToHome()
                LoginEvents.NavigateToSignUpScreen -> navController.navigateToSignUp()
                is LoginEvents.ShowToastForUnexpectedError ->TODO()
                else ->TODO()
            }
        }
    }
    LoginContent(
        loginUiState = state,
        loginInteractions = loginViewModel,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    loginUiState: LoginUiState,
    loginInteractions: LoginInteractions
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = loginUiState.username,
            onValueChange = loginInteractions::onUsernameChange,
            isError = loginUiState.usernamePlaceHolder != null,
            supportingText = {
                if (loginUiState.usernamePlaceHolder != null) {
                    Text(text = loginUiState.usernamePlaceHolder!!)
                }
            }
        )
        TextField(
            value = loginUiState.password,
            onValueChange = loginInteractions::onPasswordChange,
            isError = loginUiState.passwordPlaceHolder!=null,
            supportingText = {
                if (loginUiState.passwordPlaceHolder != null) {
                    Text(text = loginUiState.passwordPlaceHolder!!)
                }
            }
        )
        Button(onClick = loginInteractions::onCLickLogin) {
            if (loginUiState.isLoading)
                CircularProgressIndicator(
                    color = Color.White
                )
            else
                Text(text = "Login")
        }
        Button(onClick = loginInteractions::onNavigateToSignUp) {
            if (loginUiState.isLoading)
                CircularProgressIndicator(
                    color = Color.White
                )
            else
                Text(text = "to sign up")
        }
    }
}

@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    ChatifyTheme {
        LoginScreen(navController)
    }
}