package com.example.composeapp.ui.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composeapp.R
import com.example.composeapp.data.UiState
import com.example.composeapp.ui.navigation.Screen
import com.example.composeapp.ui.presentation.login.LoginViewModel
import com.example.composeapp.ui.theme.ButtonColor
import com.example.composeapp.ui.theme.ChildBgColor
import com.example.composeapp.ui.theme.OffWhite
import com.example.composeapp.ui.theme.ParentBgColor
import com.example.composeapp.ui.theme.TextBoxColor
import com.example.composeapp.ui.theme.WhiteColor
import com.example.composeapp.utils.LoggerUtils

@Composable
fun LoginPage(
    navHostController: NavHostController,
    viewModel: LoginViewModel
) {

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val passwordFocusRequester = remember {
        FocusRequester()
    }
    val localContext = LocalContext.current
    val loginState = viewModel.loginState.collectAsState()

    LaunchedEffect(key1 = loginState.value) {
        when(val status = loginState.value) {
            is UiState.Success -> {
                LoggerUtils.traceLog("Login Success - ${status.data.userId}")
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
                if (status.message.isNotEmpty()) {
                    LoggerUtils.traceLog("Error -> ${status.message}")
                    Toast.makeText(localContext, status.message, Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                // Do nothing
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
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
                DynamicText(text = "Login", style = MaterialTheme.typography.headlineLarge)
            }
            Spacer(modifier = Modifier.height(24.dp))
            /*TextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = { Text("Email", color = OffWhite) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = ChildBgColor,
                    focusedContainerColor = ChildBgColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,

                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                shape = RoundedCornerShape(4.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordFocusRequester.requestFocus()
                    }
                ),
                textStyle = TextStyle.Default.copy(
                    color = WhiteColor
                )
            )*/
            CustomEditField(
                label = "Email",
                keyboardType = KeyboardType.Email,
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
                isDoneEnabled = true,
                focusRequester = passwordFocusRequester,
                onChangeAction = {
                    password = it
                },
                onDoneAction = {
                    viewModel.validateCredentials(
                        email = email, password = password
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            /*Button(
                onClick = {
                    viewModel.validateCredentials(
                        email = email, password = password
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor
                )
            ) {
                Text("Login".uppercase())
            }*/
            CustomButton(text = "Login") {
                viewModel.validateCredentials(
                    email = email, password = password
                )
            }


            Spacer(modifier = Modifier.height(24.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = Color.Gray
                )
                TextButton(
                    onClick = {
                        navHostController.navigate(Screen.Register.route)
                    }
                ) {
                    Text(
                        text = "Register",
                        color = WhiteColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
