package com.example.composeapp.ui.presentation.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composeapp.data.UiState
import com.example.composeapp.ui.components.DynamicText
import com.example.composeapp.ui.navigation.Screen
import com.example.composeapp.ui.theme.ParentBgColor

@Composable
fun WelcomeScreen(
    navHostController: NavHostController,
    viewModel: WelcomeViewModel
) {

    val loginState = viewModel.loginState.collectAsState()

    LaunchedEffect(key1 = loginState.value) {
        if (loginState.value is UiState.Success) {
            navHostController.navigate(
                Screen.Home.route,
                builder = {
                    popUpTo(navHostController.graph.id) {
                        inclusive = true
                    }
                }
            )
        } else if (loginState.value is UiState.Error) {
            navHostController.navigate(
                Screen.Login.route,
                builder = {
                    popUpTo(Screen.Welcome.route) {
                        inclusive = true
                    }
                }

            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ParentBgColor),
        contentAlignment = Alignment.Center
    ) {
        DynamicText(
            text = "Setting things up ...",
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.titleLarge,
            overflow = TextOverflow.Ellipsis,
            color = Color.White
        )
    }
}