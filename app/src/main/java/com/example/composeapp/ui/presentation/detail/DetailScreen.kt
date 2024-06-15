package com.example.composeapp.ui.presentation.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.composeapp.R
import com.example.composeapp.data.UiState
import com.example.composeapp.data.detail.DetailModel
import com.example.composeapp.ui.components.DetailContent
import com.example.composeapp.ui.components.DynamicText
import com.example.composeapp.ui.theme.ButtonColor
import com.example.composeapp.ui.theme.ParentBgColor


@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun Material3ScaffoldLibrary(
    navHostController: NavHostController,
    viewModel: DetailViewModel
) {
    val localContext = LocalContext.current
    var isCollapsed  by remember {
        mutableStateOf(false)
    }
    val searchResult = viewModel.searchState.collectAsState()
    val userDetailState = viewModel.userDetailsFlow.collectAsState()
    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        rememberTopAppBarState()
    )
    LaunchedEffect(key1 = userDetailState.value) {
        when(val user = userDetailState.value) {
            is UiState.Success -> {
                viewModel.prepareSearchResults()
                if (viewModel.checkForAdd(user.data)) {
                    /* Hiding ads for initial release
                    viewModel.admobUtils.showInterstitial(localContext) {

                    }*/
                }
            }
            else -> {
                // Do nothing
            }
        }
        viewModel.updateUserDetails()
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = ParentBgColor)) {
        when (searchResult.value) {
            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(alignment = Alignment.Center),
                    color = Color.White
                )
            }
            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(0.dp),
                    state = listState
                ) {
                    println("Detail screen - searchKey = ${viewModel.searchKey}" +
                            " searchType = ${viewModel.searchType}" +
                            " tripId = ${viewModel.tripId}"
                    )
                    item {
                        GlideImage(
                            model = R.drawable.img_travel_detail,
                            contentDescription = "trip image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        ) {
                            it.thumbnail()
                        }
                    }
                    item {
                        DetailContent(
                            detailModel = (searchResult.value as UiState.Success<DetailModel>).data,
                            navHostController = navHostController
                        )
                    }
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
                Box(
                    Modifier.background(ButtonColor)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    Text(
                        "Another plan".uppercase(),
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.clickable {
                            viewModel.rePlanTrip()
                        }.padding(16.dp)
                            .align(Alignment.Center)
                    )
                }
            }
            is UiState.Error -> {
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp)
                ) {
                    DynamicText(
                        text = "Error loading trip details",
                        color = Color.White
                    )
                }
            }
            else -> {
                // DO nothing
            }
        }
    }
}

