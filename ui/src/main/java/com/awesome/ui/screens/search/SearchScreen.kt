package com.awesome.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.awesome.ui.ui.theme.ChatifyTheme
import com.awesome.viewmodel.search.SearchInteractions
import com.awesome.viewmodel.search.SearchUiState
import com.awesome.viewmodel.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    SearchContent(state = state, interactions = viewModel)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContent(state: SearchUiState, interactions: SearchInteractions) {
    Scaffold(
        topBar = {
            TextField(value = state.searchInput, onValueChange = interactions::onSearchInputChanged)
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)){

        }

    }
}

@Composable
@Preview(showSystemUi = true)
fun SearchScreenPreview() {
    val nav = rememberNavController()
    ChatifyTheme {
        SearchScreen(nav)
    }
}