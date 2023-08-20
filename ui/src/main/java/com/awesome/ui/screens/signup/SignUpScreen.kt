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
                SignUpEvents.NavigateToLoginScreen -> TODO()
                is SignUpEvents.ShowToastForUnexpectedError ->TODO()
                else ->TODO()
            }
        }
    }
    SignUpContent(
        username = state.username,
        usernamePlaceHolder = state.usernamePlaceHolder,
        password = state.password,
        passwordPlaceHolder = state.passwordPlaceHolder,
        email = state.email,
        emailPlaceHolder = state.emailPlaceHolder,
        fullName = state.fullName,
        fullNamePlaceHolder = state.fullNamePlaceHolder,
        onUsernameChanged = signUpViewModel::onUsernameChange,
        onPasswordChanged = signUpViewModel::onPasswordChange,
        onEmailChanged = signUpViewModel::onEmailChange,
        onFullNameChanged = signUpViewModel::onFullNameChange,
        onSignUpClicked = signUpViewModel::onCLickSignUp,
        isLoading = state.isLoading,
        onNavigateToLogin = { navController.navigateToLogin() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpContent(
    username: String,
    usernamePlaceHolder: String?,
    email: String,
    emailPlaceHolder: String?,
    fullName: String,
    fullNamePlaceHolder: String?,
    password: String,
    passwordPlaceHolder: String?,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onFullNameChanged: (String) -> Unit,
    onSignUpClicked: () -> Unit,
    isLoading: Boolean,
    onNavigateToLogin: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            label = {Text(text = "Fullname")},
            value = fullName,
            onValueChange = onFullNameChanged,
            isError = fullNamePlaceHolder != null,
            supportingText = {
                if (fullNamePlaceHolder != null) {
                    Text(text = fullNamePlaceHolder)
                }
            }
        )
        TextField(
            label = {Text(text = "Username")},
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
            label = {Text(text = "Email")},
            value = email,
            onValueChange = onEmailChanged,
            isError = emailPlaceHolder != null,
            supportingText = {
                if (emailPlaceHolder != null) {
                    Text(text = emailPlaceHolder)
                }
            }
        )
        TextField(
            label = {Text(text = "Password")},
            value = password,
            onValueChange = onPasswordChanged,
            isError = passwordPlaceHolder != null,
            supportingText = {
                if (passwordPlaceHolder != null) {
                    Text(text = passwordPlaceHolder)
                }
            }
        )
        Button(onClick = onSignUpClicked) {
            if (isLoading)
                CircularProgressIndicator(
                    color = Color.White
                )
            else
                Text(text = "SignUp")
        }
        Button(onClick = onNavigateToLogin) {
            if (isLoading)
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