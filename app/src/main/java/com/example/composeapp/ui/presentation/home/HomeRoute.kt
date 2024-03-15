package com.example.composeapp.ui.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.ui.theme.ComposeAppTheme

@Composable
fun HomeRoute(
    navHostController: NavHostController,
) {
    // Declare view model here
    val viewModel = hiltViewModel<HomeViewModel>()
    val xx=0

    val xy=0

    println("XX $xx $xy")

    HomeScreen(
        pageTitle = "Trip Planner...",
        searchPlaceHolder = "Search here",
        honeyMoonList = viewModel.honeyMoonFlow.collectAsState(),
        groupTravelList = viewModel.groupTravelFlow.collectAsState(),
        soloTravelList = viewModel.soloFlow.collectAsState(),
        spiritualTravelList = viewModel.spiritualFlow.collectAsState(),
        roadTripList = viewModel.roadTripFlow.collectAsState(),
        bikeTripList = viewModel.bikeTripFlow.collectAsState(),
        navHostController = navHostController,
        viewModel = viewModel
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAppTheme {
        //val navHostController = rememberNavController()
        //HomeRoute(navHostController = navHostController)
    }
}
