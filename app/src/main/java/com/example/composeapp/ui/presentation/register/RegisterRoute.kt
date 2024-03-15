package com.example.composeapp.ui.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.ui.theme.ComposeAppTheme

@Composable
fun RegisterRoute(
    navHostController: NavHostController
) {

    val viewModel = hiltViewModel<RegisterViewModel>()

    RegisterScreen(navHostController = navHostController, viewModel = viewModel)

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAppTheme {
        val navHostController = rememberNavController()
        RegisterRoute(navHostController = navHostController)
    }
}