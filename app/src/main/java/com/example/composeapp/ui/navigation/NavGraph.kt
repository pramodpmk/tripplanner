package com.example.composeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.composeapp.ui.components.NameBottomSheet
import com.example.composeapp.ui.presentation.detail.DetailRoute
import com.example.composeapp.ui.presentation.home.HomeRoute
import com.example.composeapp.ui.presentation.home.HomeViewModel
import com.example.composeapp.ui.presentation.login.LoginRoute
import com.example.composeapp.ui.presentation.profile.ProfileRoute
import com.example.composeapp.ui.presentation.register.RegisterRoute
import com.example.composeapp.ui.presentation.search.SearchRoute
import com.example.composeapp.ui.presentation.welcome.WelcomeRoute
import com.example.composeapp.utils.AppConstants

@Composable
fun SetupNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Welcome.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeRoute(navHostController = navHostController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument(PARAM_SEARCH) {
                    type = NavType.StringType
                },
                navArgument(SEARCH_TYPE) {
                    type = NavType.StringType
                },
                // Add params for trip id and source page
            )
        ) {
            it.arguments?.getString(PARAM_SEARCH)?.let { searchWord ->
                var searchKey = searchWord.replace("{", "")
                searchKey = searchKey.replace("}", "")
                println("navArgument>>> $searchKey")
                it.arguments?.getString(SEARCH_TYPE)?.let { searchType ->
                    val source = it.arguments?.getString(
                        AppConstants.SourcePage.ParamType
                    )?.let { src ->
                        val res = src.replace("{", "")
                        res.replace("}", "")
                    } ?: kotlin.run {
                        AppConstants.SourcePage.SRC_LISTING
                    }
                    DetailRoute(
                        navHostController = navHostController,
                        searchKey = searchKey,
                        searchType = searchType,
                        tripId = it.arguments?.getString(TRIP_ID) ?: kotlin.run {
                            ""
                        },
                        sourcePage = if (source == AppConstants.SourcePage.ParamType) {
                            AppConstants.SourcePage.SRC_LISTING
                        } else {
                            source
                        }
                    )
                }
            }
        }
        composable(
            route = Screen.Login.route
        ) {
            LoginRoute(navHostController = navHostController)
        }
        composable(
            route = Screen.Register.route
        ) {
            RegisterRoute(navHostController = navHostController)
        }
        composable(
            route = Screen.Welcome.route
        ) {
            WelcomeRoute(navHostController = navHostController)
        }
        composable(
            route = Screen.Profile.route
        ) {
            ProfileRoute(navHostController = navHostController)
        }
        composable(
            route = Screen.Search.route
        ) {
            SearchRoute(navHostController = navHostController)
        }

    }

}