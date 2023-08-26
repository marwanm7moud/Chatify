package com.awesome.ui.screens.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.awesome.ui.ui.theme.ChatifyTheme

@Composable
fun SearchScreen(navController: NavController) {
    SearchContent()
}

@Composable
fun SearchContent() {

}

@Composable
@Preview(showSystemUi = true)
fun SearchScreenPreview() {
    val nav = rememberNavController()
    ChatifyTheme {
        SearchScreen(nav)
    }
}