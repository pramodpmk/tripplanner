package com.example.composeapp.ui.presentation.welcome

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun WelcomeRoute(
    navHostController: NavHostController
) {
    val viewModel = hiltViewModel<WelcomeViewModel>()
    
    WelcomeScreen(navHostController = navHostController, viewModel = viewModel)

}