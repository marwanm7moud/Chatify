package com.awesome.ui.screens.login

import android.util.Log
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
import com.awesome.ui.ui.theme.ChatifyTheme
import com.awesome.viewmodel.loginScreen.LoginEvents
import com.awesome.viewmodel.loginScreen.LoginViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val state by loginViewModel.state.collectAsState()
    LaunchedEffect(key1 = loginViewModel.event) {
        loginViewModel.event.collectLatest {
            when (it) {
                LoginEvents.NavigateToHomeScreen -> TODO()
                LoginEvents.NavigateToSignUpScreen -> TODO()
                is LoginEvents.ShowToastForUnexpectedError ->TODO()
                else ->TODO()
            }
        }
    }
    LoginContent(
        username = state.username,
        usernamePlaceHolder = state.usernamePlaceHolder,
        password = state.password,
        onUsernameChanged = loginViewModel::onUsernameChange,
        onPasswordChanged = loginViewModel::onPasswordChange,
        onLoginClicked = loginViewModel::onCLickLogin,
        isLoading = state.isLoading,
        onNavigateToSignUp = { TODO() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    username: String,
    usernamePlaceHolder: String?,
    password: String,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    isLoading: Boolean,
    onNavigateToSignUp: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = username,
            onValueChange = onUsernameChanged,
            isError = usernamePlaceHolder != null,
            supportingText = {
                if (usernamePlaceHolder != null) {
                    Text(text = usernamePlaceHolder)
                }
            }
        )
        TextField(
            value = password,
            onValueChange = onPasswordChanged
        )
        Button(onClick = onLoginClicked) {
            if (isLoading)
                CircularProgressIndicator(
                    color = Color.White
                )
            else
                Text(text = "Login")
        }
        Button(onClick = onNavigateToSignUp) {
            if (isLoading)
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
    ChatifyTheme {
        LoginScreen()
    }
}