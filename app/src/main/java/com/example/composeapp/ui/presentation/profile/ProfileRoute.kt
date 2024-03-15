package com.example.composeapp.ui.presentation.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.ui.theme.ComposeAppTheme

@Composable
fun ProfileRoute(
    navHostController: NavHostController
) {

    val viewModel = hiltViewModel<ProfileViewModel>()

    ProfileScreen(navHostController = navHostController, viewModel = viewModel)

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAppTheme {
        val navHostController = rememberNavController()
        ProfileRoute(navHostController = navHostController)
    }
}