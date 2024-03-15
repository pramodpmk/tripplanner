package com.example.composeapp.ui.presentation.login

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.composeapp.ui.components.LoginPage

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    viewModel: LoginViewModel
) {

    LoginPage(
        navHostController = navHostController,
        viewModel = viewModel
    )
}