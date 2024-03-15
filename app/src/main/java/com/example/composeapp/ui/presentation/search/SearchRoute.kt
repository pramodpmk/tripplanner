package com.example.composeapp.ui.presentation.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.composeapp.ui.theme.ParentBgColor

@Composable
fun SearchRoute(
    navHostController: NavHostController,
    searchCallBack: (searchExpanded: Boolean) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ParentBgColor
    ) {
        SearchScreen(
            navHostController = navHostController,
            searchBoxCallBack = {
                searchCallBack.invoke(it)
            }
        )
    }
}