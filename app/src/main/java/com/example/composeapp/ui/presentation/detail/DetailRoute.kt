package com.example.composeapp.ui.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.example.composeapp.utils.LoggerUtils

@Composable
fun DetailRoute(
    navHostController: NavHostController,
    searchKey: String = "",
    searchType: String = "",
    tripId: String = "",
    sourcePage: String = "",
) {
    val viewModel = hiltViewModel<DetailViewModel>()
    LoggerUtils.traceLog("DetailRoute>>>searchKey=$searchKey searchType=$searchType" +
            " tripId=$tripId sourcePage=$sourcePage")
    viewModel.searchKey = searchKey
    viewModel.searchType = searchType
    viewModel.tripId = tripId
    viewModel.sourcePage = sourcePage
    Material3ScaffoldLibrary(
        navHostController = navHostController,
        viewModel = viewModel
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAppTheme {
        val navHostController = rememberNavController()
        DetailRoute(navHostController = navHostController)
    }
}