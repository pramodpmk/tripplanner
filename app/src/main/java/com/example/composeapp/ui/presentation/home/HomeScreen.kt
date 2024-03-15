package com.example.composeapp.ui.presentation.home

import android.annotation.SuppressLint
import android.provider.CalendarContract.Colors
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composeapp.data.UiState
import com.example.composeapp.data.home.TripItemModel
import com.example.composeapp.ui.components.AdmobBanner
import com.example.composeapp.ui.components.DynamicText
import com.example.composeapp.ui.components.HomeHighlightedRow
import com.example.composeapp.ui.components.HomeListRow
import com.example.composeapp.ui.components.MultiLineRow
import com.example.composeapp.ui.components.SearchBox
import com.example.composeapp.ui.navigation.LOGIN_ROUTE
import com.example.composeapp.ui.navigation.Screen
import com.example.composeapp.ui.presentation.search.SearchScreen
import com.example.composeapp.ui.theme.OffWhite
import com.example.composeapp.ui.theme.ParentBgColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    pageTitle: String,
    searchPlaceHolder: String,
    honeyMoonList: State<MutableList<TripItemModel>>,
    groupTravelList: State<MutableList<TripItemModel>>,
    soloTravelList: State<MutableList<TripItemModel>>,
    spiritualTravelList: State<MutableList<TripItemModel>>,
    roadTripList: State<MutableList<TripItemModel>>,
    bikeTripList: State<MutableList<TripItemModel>>,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: HomeViewModel
) {
    Scaffold {
        var searchBoxClose by remember {
            mutableStateOf(true)
        }
        var userDetailsState = viewModel.userDetailsFlow.collectAsState()
        val scope = rememberCoroutineScope()

        LaunchedEffect(key1 = Unit, block = {
            viewModel.loadUserDetails()
        })

        Column(
            modifier = Modifier.background(
                color = ParentBgColor
            )
        ) {
            AnimatedVisibility(visible = searchBoxClose) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location",
                                modifier = Modifier.size(24.dp),
                                tint = Color.White
                            )
                            val details = userDetailsState.value
                            DynamicText(
                                text = if (details is UiState.Success) {
                                    details.data.location
                                } else {
                                    "Select Location"
                                },
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.titleLarge,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.White
                            )
                        }
                        IconButton(
                            onClick = {
                            navHostController.navigate(Screen.Profile.route)
                        }) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Account",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
            BoxWithConstraints {
                SearchScreen(
                    navHostController = navHostController,
                    searchBoxCallBack = {
                        searchBoxClose = it
                    }
                )
            }
            LazyColumn(modifier = modifier) {
                if (honeyMoonList.value.size > 0) {
                    item {
                        HomeHighlightedRow(
                            itemList = honeyMoonList.value,
                            navHostController = navHostController
                        )
                    }
                }
                item {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        AdmobBanner(
                            modifier = Modifier.fillMaxWidth(),
                            adId = viewModel.admobUtils.TEST_BANNER_AD
                        )
                    }
                }
                if (honeyMoonList.value.size > 0) {
                    item {
                        HomeListRow(
                            title = "Honeymoon packages",
                            description = "Explore honeymoon packages",
                            itemList = honeyMoonList.value,
                            navHostController = navHostController
                        )
                    }
                }
                if (groupTravelList.value.size > 0) {
                    item {
                        MultiLineRow(
                            title = "Group travel packages",
                            description = "Explore group travel packages",
                            itemList = groupTravelList.value,
                            navHostController = navHostController
                        )
                    }
                }
                item {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        AdmobBanner(
                            modifier = Modifier.fillMaxWidth(),
                            adId = viewModel.admobUtils.TEST_BANNER_AD
                        )
                    }
                }
                if (soloTravelList.value.size > 0) {
                    item {
                        HomeListRow(
                            title = "Solo travel packages",
                            description = "Explore solo travel packages",
                            itemList = soloTravelList.value,
                            navHostController = navHostController
                        )
                    }
                }
                if (roadTripList.value.size > 0) {
                    item {
                        MultiLineRow(
                            title = "Road trips",
                            description = "Explore road trips",
                            itemList = roadTripList.value,
                            navHostController = navHostController
                        )
                    }
                }
                item {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        AdmobBanner(
                            modifier = Modifier.fillMaxWidth(),
                            adId = viewModel.admobUtils.TEST_BANNER_AD
                        )
                    }
                }
                if (bikeTripList.value.size > 0) {
                    item {
                        HomeListRow(
                            title = "Bike trip",
                            description = "Explore bike trips",
                            itemList = bikeTripList.value,
                            navHostController = navHostController
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }
        }
    }
}
