package com.example.composeapp.ui.presentation.register

import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composeapp.data.UiState
import com.example.composeapp.ui.components.CustomButton
import com.example.composeapp.ui.components.CustomEditField
import com.example.composeapp.ui.components.DynamicText
import com.example.composeapp.ui.components.PasswordInputField
import com.example.composeapp.ui.navigation.Screen
import com.example.composeapp.ui.theme.ParentBgColor
import com.example.composeapp.utils.LoggerUtils

@Composable
fun RegisterScreen(
    navHostController: NavHostController,
    viewModel: RegisterViewModel
) {
    RegisterPage(
        navHostController = navHostController,
        viewModel = viewModel
    )
}

@Composable
fun RegisterPage(
    navHostController: NavHostController,
    viewModel: RegisterViewModel
) {

    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var location by remember {
        mutableStateOf("")
    }
    val passwordFocusRequester = remember {
        FocusRequester()
    }
    val emailFocusRequester = remember {
        FocusRequester()
    }
    val locationFocusRequester = remember {
        FocusRequester()
    }
    val localContext = LocalContext.current
    val registerState = viewModel.registerState.collectAsState()
    LaunchedEffect(key1 = registerState.value) {
        when (val res = registerState.value) {
            is UiState.Success -> {
                LoggerUtils.traceLog("Success -> ${res.data}")
                navHostController.navigate(
                    Screen.Home.route,
                    builder = {
                        popUpTo(navHostController.graph.id) {
                            inclusive = true
                        }
                    }
                )
            }
            is UiState.Error -> {
                if (res.message.isNotEmpty()) {
                    LoggerUtils.traceLog("Error -> ${res.message}")
                    Toast.makeText(localContext, res.message, Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                // Do nothing
            }
        }
    }

    Box(Modifier.fillMaxSize()
        .background(ParentBgColor)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                DynamicText(text = "Register", style = MaterialTheme.typography.headlineLarge)
            }
            Spacer(modifier = Modifier.height(24.dp))
            /*OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        emailFocusRequester.requestFocus()
                    }
                )
            )*/
            CustomEditField(
                label = "Name",
                keyboardType = KeyboardType.Text,
                onChangeAction = {
                    name = it
                },
                onNextAction = {
                    emailFocusRequester.requestFocus()
                }, onDoneAction = {

                }
            )


            Spacer(modifier = Modifier.height(16.dp))
            /*OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(emailFocusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordFocusRequester.requestFocus()
                    }
                )
            )*/
            CustomEditField(
                label = "Email",
                keyboardType = KeyboardType.Email,
                focusRequester = emailFocusRequester,
                onChangeAction = {
                    email = it
                },
                onNextAction = {
                    passwordFocusRequester.requestFocus()
                }, onDoneAction = {

                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            PasswordInputField(
                text = "Password",
                focusRequester = passwordFocusRequester,
                onChangeAction = {
                    password = it
                },
                onDoneAction = {
                    locationFocusRequester.requestFocus()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            /*OutlinedTextField(
                value = location,
                onValueChange = {
                    location = it
                },
                label = { Text("Location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(locationFocusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Submit action
                        viewModel.validateInput(
                            name = name,
                            email = email,
                            password = password,
                            location = location
                        )
                    }
                )
            )*/
            CustomEditField(
                label = "Location",
                keyboardType = KeyboardType.Text,
                focusRequester = locationFocusRequester,
                isDoneEnabled = true,
                onChangeAction = {
                    location = it
                },
                onNextAction = {

                },
                onDoneAction = {
                    viewModel.validateInput(
                        name = name,
                        email = email,
                        password = password,
                        location = location
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(text = "Register") {
                viewModel.validateInput(
                    name = name,
                    email = email,
                    password = password,
                    location = location
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}