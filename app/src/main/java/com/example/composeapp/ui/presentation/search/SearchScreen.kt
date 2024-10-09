package com.example.composeapp.ui.presentation.search

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.data.UiState
import com.example.composeapp.data.search.SelectionChipModel
import com.example.composeapp.ui.components.CustomButton
import com.example.composeapp.ui.components.FieldPicker
import com.example.composeapp.ui.components.SearchInputBox
import com.example.composeapp.ui.components.SelectionChipGroup
import com.example.composeapp.ui.navigation.PARAM_SEARCH
import com.example.composeapp.ui.navigation.SEARCH_TYPE
import com.example.composeapp.ui.navigation.Screen
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.example.composeapp.ui.theme.ParentBgColor
import com.example.composeapp.utils.AppConstants
import com.example.composeapp.utils.AppUtils
import com.example.composeapp.utils.LoggerUtils
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navHostController: NavHostController,
) {

    val viewModel = hiltViewModel<SearchViewModel>()

    val tripTypeList = viewModel.searchListFlow.collectAsState()
    val tripDaysList = viewModel.numOfDaysFlow.collectAsState()
    val searchQuery = viewModel.searchQuery.collectAsState(
        UiState.Idle
    )

    var text by remember {
        mutableStateOf("")
    }
    var tripType by remember {
        mutableStateOf("")
    }
    var selectedDays by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(true)
    }
    val localContext = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.provideTripTypeList()
        viewModel.searchQuery.collectLatest { it ->
            when(val state = it) {
                is UiState.Success -> {
                    navHostController.navigate(
                        route = Screen.Detail.route
                            .replace(
                                oldValue = PARAM_SEARCH,
                                newValue = state.data
                            )
                            .replace(
                                oldValue = SEARCH_TYPE,
                                newValue = tripType
                            )
                            .replace(
                                oldValue = AppConstants.SourcePage.ParamType,
                                newValue = AppConstants.SourcePage.SRC_SEARCH
                            )
                    )
                }
                is UiState.Error -> {
                    // Show error message
                    LoggerUtils.traceLog(state.message)
                    Toast.makeText(localContext, state.message, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // DO nothing
                }
            }
        }
    }
    /*LaunchedEffect(key1 = searchQuery.value) {
        LoggerUtils.traceLog("Inside launched effect searchQuery.value")
        when(val state = searchQuery.value) {
            is UiState.Success -> {
                navHostController.navigate(
                    route = Screen.Detail.route
                        .replace(
                            oldValue = PARAM_SEARCH,
                            newValue = state.data
                        )
                        .replace(
                            oldValue = SEARCH_TYPE,
                            newValue = tripType
                        )
                        .replace(
                            oldValue = AppConstants.SourcePage.ParamType,
                            newValue = AppConstants.SourcePage.SRC_SEARCH
                        )
                )
            }
            is UiState.Error -> {
                // Show error message
                LoggerUtils.traceLog(state.message)
                Toast.makeText(localContext, state.message, Toast.LENGTH_SHORT).show()
            }
            else -> {
                // DO nothing
            }
        }
    }*/

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = ParentBgColor)) {
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            item {
                SearchInputBox(
                    placeHolder = "Type anything...",
                    onTextChange = {
                        text = it
                    },
                    searchForText = {
                        // Navigate to detail page
                    }
                ) 
            }
            if (tripTypeList.value.isNotEmpty()) {
                item {
                    Box(
                        modifier = Modifier.padding(
                            start = 8.dp,
                            end = 8.dp,
                            top = 8.dp
                        )
                    ) {
                        SelectionChipGroup(
                            itemList = tripTypeList.value,
                            onChipSelection = {
                                tripType = it.id
                            })
                    }
                }
            }
            if (tripDaysList.value.isNotEmpty()) {
                item {
                    Box(modifier = Modifier.padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = 8.dp
                    )) {
                        FieldPicker(
                            itemList = tripDaysList.value,
                            onItemClick = {
                                selectedDays = it.title
                            }
                        )
                    }
                }
            }
            item {
                Box(modifier = Modifier.padding(
                    start = 18.dp,
                    end = 18.dp,
                    top = 14.dp)
                ) {
                    CustomButton(
                        text = "Generate Plan",
                        style = MaterialTheme.typography.titleMedium
                    ) {
                        LoggerUtils.traceLog("Button Clicked")
                        LoggerUtils.traceLog("days:$selectedDays place:$text type:$tripType")
                        // Go to detail page
                        viewModel.planTrip(
                            numDays = selectedDays,
                            place = text,
                            tripType = tripType
                        )
                    }
                }
            }
            /*LazyColumn {
                if (text != "" && text.length > 2) {
                    searchList.value.forEach { _item ->
                        item {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .clickable {
                                        if (text != "") {
                                            text = ""
                                        }
                                        // Go to detail page
                                        navHostController.navigate(
                                            route = Screen.Detail.route
                                                .replace(
                                                    oldValue = PARAM_SEARCH,
                                                    newValue = _item.title
                                                )
                                                .replace(
                                                    oldValue = SEARCH_TYPE,
                                                    newValue = _item.tripType.toString()
                                                )
                                                .replace(
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
            }*/
        }
        ElevatedButton(
            onClick = {
                // Close the detail page
                navHostController.popBackStack()
            },
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.TopEnd)
                .padding(8.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            border = BorderStroke(width = 1.dp, color = Color.Black)
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "clear")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    ComposeAppTheme {
        val navHostController = rememberNavController()
        SearchScreen(navHostController = navHostController)
    }
}
