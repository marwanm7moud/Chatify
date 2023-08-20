package com.awesome.ui.screens.signup

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.awesome.ui.screens.login.navigateToLogin
import com.awesome.ui.ui.theme.ChatifyTheme
import com.awesome.viewmodel.signUpScreen.SignUpEvents
import com.awesome.viewmodel.signUpScreen.SignUpInteractions
import com.awesome.viewmodel.signUpScreen.SignUpUiState
import com.awesome.viewmodel.signUpScreen.SignUpViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun SignUpScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel = hiltViewModel()
) {
    val state by signUpViewModel.state.collectAsState()
    LaunchedEffect(key1 = signUpViewModel.event) {
        signUpViewModel.event.collectLatest {
            when (it) {
                SignUpEvents.NavigateToHomeScreen -> TODO()
                SignUpEvents.NavigateToLoginScreen -> navController.navigateToLogin()
                is SignUpEvents.ShowToastForUnexpectedError -> TODO()
                else -> TODO()
            }
        }
    }
    SignUpContent(
        signUpUiState = state,
        signUpInteractions = signUpViewModel,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpContent(
    signUpUiState: SignUpUiState,
    signUpInteractions: SignUpInteractions,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            label = { Text(text = "Fullname") },
            value = signUpUiState.fullName,
            onValueChange = signUpInteractions::onFullNameChange,
            isError = signUpUiState.fullNamePlaceHolder != null,
            supportingText = {
                if (signUpUiState.fullNamePlaceHolder != null) {
                    Text(text = signUpUiState.fullNamePlaceHolder!!)
                }
            }
        )
        TextField(
            label = { Text(text = "Username") },
            value = signUpUiState.username,
            onValueChange = signUpInteractions::onUsernameChange,
            isError = signUpUiState.usernamePlaceHolder != null,
            supportingText = {
                if (signUpUiState.usernamePlaceHolder != null) {
                    Text(text = signUpUiState.usernamePlaceHolder!!)
                }
            }
        )
        TextField(
            label = { Text(text = "Email") },
            value = signUpUiState.email,
            onValueChange = signUpInteractions::onEmailChange,
            isError = signUpUiState.emailPlaceHolder != null,
            supportingText = {
                if (signUpUiState.emailPlaceHolder != null) {
                    Text(text = signUpUiState.emailPlaceHolder!!)
                }
            }
        )
        TextField(
            label = { Text(text = "Password") },
            value = signUpUiState.password,
            onValueChange = signUpInteractions::onPasswordChange,
            isError = signUpUiState.passwordPlaceHolder != null,
            supportingText = {
                if (signUpUiState.passwordPlaceHolder != null) {
                    Text(text = signUpUiState.passwordPlaceHolder!!)
                }
            }
        )
        Button(onClick = signUpInteractions::onCLickSignUp) {
            if (signUpUiState.isLoading)
                CircularProgressIndicator(
                    color = Color.White
                )
            else
                Text(text = "SignUp")
        }
        Button(onClick = signUpInteractions::onNavigateToLogin) {
            if (signUpUiState.isLoading)
                CircularProgressIndicator(
                    color = Color.White
                )
            else
                Text(text = "to login")
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun SignUpScreenPreview() {
    val navController = rememberNavController()
    ChatifyTheme {
        SignUpScreen(navController)
    }
}