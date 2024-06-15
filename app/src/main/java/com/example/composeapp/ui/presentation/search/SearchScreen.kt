package com.example.composeapp.ui.presentation.search

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.composeapp.ui.navigation.PARAM_SEARCH
import com.example.composeapp.ui.navigation.SEARCH_TYPE
import com.example.composeapp.ui.navigation.Screen
import com.example.composeapp.utils.AppConstants

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navHostController: NavHostController,
    searchBoxCallBack: (isExpanded: Boolean) -> Unit
) {

    val viewModel = hiltViewModel<SearchViewModel>()

    val searchList = viewModel.searchListFlow.collectAsState()

    var text by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        query = text,
        onQueryChange = {
            text = it
            if (text != "") {
                viewModel.searchAction(text)
            } else {
                searchList.value.clear()
            }
        },
        onSearch = {
            println("Search - $it")
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
            searchBoxCallBack.invoke(!it)
        },
        placeholder = {
            Text(text = "Search...")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon"
            )
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (text.isBlank()) {
                            active = false
                            searchBoxCallBack.invoke(true)
                        } else {
                            text = ""
                            searchList.value.clear()
                        }
                    },
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Close icon"
                )
            }
        }
    ) {
        LazyColumn {
            /*if (text == "") {
                item {
                    Row(Modifier.padding(8.dp)) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Icon refresh")
                        Text(text = "Recent searched item")
                    }
                }
                item {
                    Row(Modifier.padding(12.dp)) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Icon refresh")
                        Text(text = "Recent searched item")
                    }
                }
            }*/
            if (text != "" && text.length > 2) {
                searchList.value.forEach { _item ->
                    item {
                        Row(
                            modifier = Modifier.padding(12.dp)
                                .clickable {
                                    if (text != "") {
                                        text = ""
                                    }
                                    // Go to detail page
                                    navHostController.navigate(
                                        route = Screen.Detail.route.replace(
                                            oldValue = PARAM_SEARCH,
                                            newValue = _item.title
                                        ).replace(
                                            oldValue = SEARCH_TYPE,
                                            newValue = _item.tripType.toString()
                                        ).replace(
                                            oldValue = AppConstants.SourcePage.ParamType,
                                            newValue = AppConstants.SourcePage.SRC_SEARCH
                                        )
                                    )
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Icon refresh"
                            )
                            Text(text = _item.title)
                        }
                    }
                }
            }
        }
    }
}