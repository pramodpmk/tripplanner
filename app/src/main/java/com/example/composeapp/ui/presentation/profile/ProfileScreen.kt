package com.example.composeapp.ui.presentation.profile

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composeapp.data.UiState
import com.example.composeapp.data.user.UserDetails
import com.example.composeapp.ui.components.DynamicText
import com.example.composeapp.ui.components.LogoutPopup
import com.example.composeapp.ui.components.NameBottomSheet
import com.example.composeapp.ui.components.ProfileGrid
import com.example.composeapp.ui.components.ProfileGridItem
import com.example.composeapp.ui.navigation.Screen
import com.example.composeapp.ui.theme.ParentBgColor
import com.example.composeapp.utils.AppConstants
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    viewModel: ProfileViewModel
) {
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var location by remember {
        mutableStateOf("")
    }
    var userDetails: UserDetails? by remember {
        mutableStateOf(null)
    }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    var showLogoutPopup by remember {
        mutableStateOf(false)
    }
    var editField by remember {
        mutableStateOf("")
    }
    val changeState = viewModel.loginState.collectAsState()
    val userDetailState = viewModel.userDetailsFlow.collectAsState()
    val localContext = LocalContext.current
    val logoutState = viewModel.logoutFlow.collectAsState()
    val updateDetailsState = viewModel.updateDetailsFlow.collectAsState()

    LaunchedEffect(key1 = changeState.value, block = {
        when (val state = changeState.value) {
            is UiState.Success -> {
                Toast.makeText(localContext, "Password Changed", Toast.LENGTH_SHORT).show()
            }

            is UiState.Error -> {
                Toast.makeText(localContext, state.message, Toast.LENGTH_SHORT).show()
            }

            else -> {

            }
        }
    })
    LaunchedEffect(key1 = updateDetailsState.value, block = {
        when (val state = updateDetailsState.value) {
            is UiState.Success -> {
                showBottomSheet = false
                Toast.makeText(localContext, state.data, Toast.LENGTH_SHORT).show()
            }

            is UiState.Error -> {
                Toast.makeText(localContext, state.message, Toast.LENGTH_SHORT).show()
            }

            else -> {

            }
        }
    })
    LaunchedEffect(key1 = userDetailState.value, block = {
        when (val state = userDetailState.value) {
            is UiState.Success -> {
                userDetails = state.data
                name = state.data.name
                email = state.data.email
                location = state.data.location
            }

            is UiState.Error -> {
                // Do nothing
            }

            else -> {

            }
        }
    })
    LaunchedEffect(key1 = logoutState.value) {
        if (logoutState.value is UiState.Success) {
            navHostController.navigate(
                Screen.Login.route,
                builder = {
                    popUpTo(navHostController.graph.id) {
                        inclusive = true
                    }
                }
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = ParentBgColor)
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.size(48.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "account",
                    modifier = Modifier.size(104.dp)
                        .padding(start = 8.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    DynamicText(
                        text = name,
                        modifier = Modifier.padding(start = 16.dp),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Image(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "account",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                scope.launch {
                                    if (userDetails != null) {
                                        editField = AppConstants.EditField.NAME
                                        showBottomSheet = true
                                    } else {
                                        navHostController.navigate(
                                            Screen.Login.route
                                        )
                                    }
                                }
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
            ProfileGrid(
                navHostController = navHostController,
                emailCallBack = {
                    scope.launch {
                        if (userDetails != null) {
                            editField = AppConstants.EditField.EMAIL
                            showBottomSheet = true
                        } else {
                            navHostController.navigate(
                                Screen.Login.route
                            )
                        }
                    }
                },
                passwordCallBack = {
                    scope.launch {
                        if (userDetails != null) {
                            editField = AppConstants.EditField.PASSWORD
                            showBottomSheet = true
                        } else {
                            navHostController.navigate(
                                Screen.Login.route
                            )
                        }
                    }
                },
                locationCallBack = {
                    scope.launch {
                        if (userDetails != null) {
                            editField = AppConstants.EditField.LOCATION
                            showBottomSheet = true
                        } else {
                            navHostController.navigate(
                                Screen.Login.route
                            )
                        }
                    }
                },
                logoutCallBack = {
                    if (userDetails != null) {
                        // Logout user
                        showLogoutPopup = true
                    } else {
                        navHostController.navigate(
                            Screen.Login.route
                        )
                    }
                }
            )
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
        if (showBottomSheet) {
            NameBottomSheet(
                editField = editField,
                userDetails = userDetails,
                navHostController = navHostController,
                onDismiss = {
                    showBottomSheet = false
                },
                onDoneEmail = {
                },
                onDoneName = { _name ->
                    name = _name
                    viewModel.updateUserDetails(name = _name)
                },
                onDonePassword = { password, oldPassword ->
                    viewModel.changePassword(password, oldPassword)
                },
                onDoneLocation = {
                    location = it
                    viewModel.updateUserDetails(location = it)
                }
            )
        }
        if (showLogoutPopup) {
            LogoutPopup(
                yesAction = {
                    showLogoutPopup = false
                    viewModel.logoutUser()
                },
                noAction = {
                    showLogoutPopup = false
                }
            )
        }
    }
}
