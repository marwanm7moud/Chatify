package com.awesome.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.awesome.ui.navigation.MainNavHost
import com.awesome.ui.screens.login.LoginScreen

import com.awesome.ui.ui.theme.ChatifyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatifyTheme {
                val navController = rememberNavController()
                MainNavHost(navController = navController)
            }
        }
    }
}