package com.example.composeapp.ui.components

import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.composeapp.R
import com.example.composeapp.data.UiState
import com.example.composeapp.data.user.UserDetails
import com.example.composeapp.ui.presentation.profile.ProfileViewModel
import com.example.composeapp.ui.theme.ButtonColor
import com.example.composeapp.ui.theme.LineColor
import com.example.composeapp.ui.theme.OffWhite
import com.example.composeapp.ui.theme.ParentBgColor
import com.example.composeapp.ui.theme.TextBoxColor
import com.example.composeapp.ui.theme.WhiteColor
import com.example.composeapp.utils.AppConstants
import com.example.composeapp.utils.LoggerUtils

@Composable
fun ProfileGridItem(
    itemType: Int,
    title: String,
    imageVector: ImageVector,
    clickAction: () -> Unit
) {
    Box(modifier = Modifier.clickable {
        clickAction.invoke()
    }
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = imageVector,
                        contentDescription = "account",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                    DynamicText(
                        text = title,
                        modifier = Modifier.padding(start = 16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Image(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "arrow",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }
}

@Composable
fun ProfileGrid(
    navHostController: NavHostController,
    emailCallBack: () -> Unit,
    passwordCallBack: () -> Unit,
    locationCallBack: () -> Unit,
    termsCallBack: () -> Unit,
    policyCallBack: () -> Unit,
    deleteCallBack: () -> Unit,
    logoutCallBack: () -> Unit,
) {
    Spacer(modifier = Modifier.height(16.dp))
    ProfileGridItem(
        itemType = 1,
        title = "Email",
        imageVector = Icons.Default.Email
    ) {
        // Open bottom sheet using scaffold
        emailCallBack.invoke()
    }
    ViewLine()
    ProfileGridItem(
        itemType = 1,
        title = "Change Password",
        imageVector = Icons.Default.Lock
    ) {
        passwordCallBack.invoke()
    }
    ViewLine()
    ProfileGridItem(
        itemType = 1,
        title = "Location",
        imageVector = Icons.Default.LocationOn
    ) {
        locationCallBack.invoke()
    }
    ViewLine()
    ProfileGridItem(
        itemType = 1,
        title = "Terms & Conditions",
        imageVector = Icons.Default.Info
    ) {
        termsCallBack.invoke()
    }
    ViewLine()
    ProfileGridItem(
        itemType = 1,
        title = "Privacy Policy",
        imageVector = Icons.Default.Lock
    ) {
        policyCallBack.invoke()
    }
    ViewLine()
    ProfileGridItem(
        itemType = 1,
        title = "Delete Account",
        imageVector = Icons.Default.Delete
    ) {
        deleteCallBack.invoke()
    }
    ViewLine()
    ProfileGridItem(
        itemType = 1,
        title = "Logout",
        imageVector = Icons.Default.ExitToApp
    ) {
        logoutCallBack.invoke()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameBottomSheet(
    editField: String,
    userDetails: UserDetails?,
    navHostController: NavHostController,
    onDismiss: () -> Unit,
    onDoneEmail: (email: String) -> Unit,
    onDoneName: (name: String) -> Unit,
    onDonePassword: (password: String, oldPassword: String) -> Unit,
    onDoneLocation: (location: String) -> Unit
) {
    ModalBottomSheet(
        containerColor = ParentBgColor,
        onDismissRequest = {
            onDismiss.invoke()
        }
    ) {
        if (editField == AppConstants.EditField.EMAIL) {
            NonEditableField(
                input = userDetails?.email ?: "",
                title = "Change Email",
                image = Icons.Default.Email
            )
        } else if (editField == AppConstants.EditField.NAME) {
            EditSingleTextField(
                label = "Name",
                input = userDetails?.name ?: "",
                title = "Change Name",
                image = Icons.Default.Person
            ) {
                onDoneName.invoke(it)
            }
        } else if (editField == AppConstants.EditField.LOCATION) {
            EditSingleTextField(
                label = "Location",
                input = userDetails?.location ?: "",
                title = "Change Location",
                image = Icons.Default.LocationOn
            ) {
                onDoneLocation.invoke(it)
            }
        } else if (editField == AppConstants.EditField.PASSWORD) {
            EditPasswordField(
                title = "Change Password",
                image = Icons.Default.Lock
            ) { password, oldPassword ->
                onDonePassword.invoke(password, oldPassword)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthenticateBottomSheet(
    viewModel: ProfileViewModel,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
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
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    val reAuthenticate = viewModel.reAuthenticateState.collectAsState()
    val localContext = LocalContext.current

    LaunchedEffect(key1 = reAuthenticate.value) {
        when (val status = reAuthenticate.value) {
            is UiState.Success -> {
                onSuccess.invoke()
            }

            is UiState.Error -> {
                if (status.message.isNotEmpty()) {
                    LoggerUtils.traceLog("Error -> ${status.message}")
                    Toast.makeText(localContext, status.message, Toast.LENGTH_SHORT).show()
                }
                //onDismiss.invoke()
            }

            else -> {
                // Do nothing
            }
        }
    }
    ModalBottomSheet(
        containerColor = ParentBgColor,
        onDismissRequest = {
            onDismiss.invoke()
        }
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(ParentBgColor)
        ) {
            Column {
                DynamicText(
                    text = "Re-Authenticate to proceed",
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(26.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            imageVector = Icons.Default.MailOutline,
                            contentDescription = "account",
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
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
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = "account",
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        PasswordInputField(
                            text = "Password",
                            isDoneEnabled = true,
                            focusRequester = passwordFocusRequester,
                            onChangeAction = {
                                password = it
                            },
                            onDoneAction = {
                                viewModel.reAuthenticateUser(
                                    email = email, password = password
                                )
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                CustomButton(text = "Proceed") {
                    viewModel.reAuthenticateUser(email, password)
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                )
            }
        }
    }
}

@Composable
fun EditSingleTextField(
    label: String,
    input: String,
    title: String,
    image: ImageVector,
    clickAction: (output: String) -> Unit,
) {
    var inputState by remember {
        mutableStateOf(input)
    }

    Box(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column {
            DynamicText(
                text = title,
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(26.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = image,
                        contentDescription = "account",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    CustomEditField(
                        label = label,
                        textValue = input,
                        keyboardType = KeyboardType.Email,
                        isDoneEnabled = true,
                        onChangeAction = {
                            inputState = it
                        },
                        onNextAction = {

                        },
                        onDoneAction = {
                            clickAction.invoke(inputState)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            CustomButton(text = "Save") {
                clickAction.invoke(inputState)
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp))
        }
    }
}

@Composable
fun NonEditableField(
    input: String,
    title: String,
    image: ImageVector
) {
    var inputState by remember {
        mutableStateOf(input)
    }

    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(ParentBgColor)
    ) {
        Column {
            DynamicText(
                text = title,
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(26.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = image,
                        contentDescription = "account",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = inputState,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth(),
                        style = LocalTextStyle.current
                    )
                }
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp))
        }
    }
}


@Composable
fun EditPasswordField(
    title: String,
    image: ImageVector,
    clickAction: (output: String, oldValue: String) -> Unit,
) {
    var inputState by remember {
        mutableStateOf("")
    }
    var oldState by remember {
        mutableStateOf("")
    }
    val focusRequester = remember {
        FocusRequester()
    }

    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(ParentBgColor)
    ) {
        Column {
            DynamicText(
                text = title,
                modifier = Modifier.padding(start = 16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(26.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = image,
                        contentDescription = "account",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(WhiteColor)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    PasswordInputField(
                        text = "New Password",
                        isDoneEnabled = false,
                        onChangeAction = {
                            oldState = it
                        },
                        onDoneAction = {
                            focusRequester.requestFocus()
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = image,
                        contentDescription = "account",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(WhiteColor)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    PasswordInputField(
                        text = "Re-Type Password",
                        isDoneEnabled = true,
                        focusRequester = focusRequester,
                        onChangeAction = {
                            inputState = it
                        },
                        onDoneAction = {
                            clickAction.invoke(inputState, oldState)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            CustomButton(text = "Save") {
                clickAction.invoke(inputState, oldState)
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp))
        }
    }
}

@Composable
fun LogoutPopup(
    title: String = "",
    description: String = "",
    yesAction: () -> Unit,
    noAction: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = title)
        },
        text = {
            Box {
                Text(text = description)
            }
        },
        onDismissRequest = {
            noAction.invoke()
        }, confirmButton = {
            Text(
                text = "Yes",
                modifier = Modifier
                    .clickable {
                        yesAction.invoke()
                    }
                    .padding(16.dp)
            )
        },
        dismissButton = {
            Text(
                text = "No",
                modifier = Modifier
                    .clickable {
                        noAction.invoke()
                    }
                    .padding(16.dp)
            )
        })
}

@Composable
fun ViewLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 8.dp,
                bottom = 8.dp
            )
    ) {
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(LineColor)
        )
    }
}

@Composable
fun WebViewPage(
    htmlText: String,
    title: String,
    callBack: () -> Unit
) {
    Column {
        Box(Modifier.background(ParentBgColor)) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        webViewClient = WebViewClient()

                        settings.loadWithOverviewMode = true
                        settings.useWideViewPort = true
                        settings.setSupportZoom(true)
                    }
                },
                update = { webView ->
                    //webView.loadUrl("https://www.ldoceonline.com/")
                    webView.loadDataWithBaseURL(
                        null,
                        htmlText,
                        "text/html",
                        "utf-8",
                        null
                    )
                }
            )
            ElevatedButton(
                onClick = {
                    // Close the detail page
                    callBack.invoke()
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
}
