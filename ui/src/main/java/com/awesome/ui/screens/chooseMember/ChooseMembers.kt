package com.awesome.ui.screens.chooseMember

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.awesome.ui.screens.login.navigateToLogin
import com.awesome.ui.screens.search.navigateToSearch
import com.awesome.viewmodel.chooseMembers.ChooseMemberEvents
import com.awesome.viewmodel.chooseMembers.ChooseMemberInteraction
import com.awesome.viewmodel.chooseMembers.ChooseMemberUiState
import com.awesome.viewmodel.chooseMembers.ChooseMemberViewModel
import com.awesome.viewmodel.home.HomeEvents
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChooseMembers(
    navController: NavController,
    viewModel: ChooseMemberViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(key1 = viewModel.event) {
        viewModel.event.collectLatest {
            when (it) {
                ChooseMemberEvents.NavigateBackWithNewChat -> navController.popBackStack()
                else -> {}
            }
        }
    }
    ChooseMembersContent(state, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseMembersContent(state: ChooseMemberUiState, interactions: ChooseMemberInteraction) {
    Scaffold(
        topBar = {
            Row() {
                TextField(
                    value = state.searchInput,
                    onValueChange = interactions::onSearchInputChanged
                )
                TextButton(onClick = interactions::onClickDone) {
                    Text(text = "Done")
                }
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(state.users.size) {
                val user = state.users[it]
                Card(
                    modifier = if (state.selectedUser == user) Modifier.border(
                        1.dp,
                        color = Color.Black
                    ) else Modifier
                ) {
                    Row(modifier = Modifier.clickable {
                        interactions.onClickUser(user)
                    }) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                        )
                        Text(text = user.fullName)
                    }
                }
            }
        }

    }
}