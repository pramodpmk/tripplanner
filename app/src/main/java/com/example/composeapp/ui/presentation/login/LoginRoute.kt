package com.example.composeapp.ui.presentation.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun LoginRoute(
    navHostController: NavHostController
) {
    val viewModel = hiltViewModel<LoginViewModel>()

    LoginScreen(navHostController = navHostController, viewModel = viewModel)
}