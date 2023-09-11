package com.awesome.ui.screens.chooseMember

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.awesome.viewmodel.chooseMembers.ChooseMemberInteraction
import com.awesome.viewmodel.chooseMembers.ChooseMemberUiState
import com.awesome.viewmodel.chooseMembers.ChooseMemberViewModel

@Composable
fun ChooseMembers(
    navController: NavController,
    viewModel: ChooseMemberViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    ChooseMembersContent(state, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseMembersContent(state: ChooseMemberUiState, interactions: ChooseMemberInteraction) {
    Scaffold(
        topBar = {
            TextField(value = state.searchInput, onValueChange = interactions::onSearchInputChanged)
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {

        }

    }
}