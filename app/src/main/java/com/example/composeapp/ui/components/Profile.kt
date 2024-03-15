package com.example.composeapp.ui.components

import android.provider.ContactsContract.CommonDataKinds.StructuredName
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composeapp.data.user.UserDetails
import com.example.composeapp.ui.theme.ButtonColor
import com.example.composeapp.ui.theme.ChildBgColor
import com.example.composeapp.ui.theme.LineColor
import com.example.composeapp.ui.theme.OffWhite
import com.example.composeapp.ui.theme.ParentBgColor
import com.example.composeapp.ui.theme.TextBoxColor
import com.example.composeapp.ui.theme.WhiteColor
import com.example.composeapp.utils.AppConstants

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
                input = userDetails?.name ?: "",
                title = "Change Name",
                image = Icons.Default.Person
            ) {
                onDoneName.invoke(it)
            }
        } else if (editField == AppConstants.EditField.LOCATION) {
            EditSingleTextField(
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

@Composable
fun EditSingleTextField(
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
                    .height(50.dp)
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
                    EditText(
                        value = inputState,
                        onValueChange = {
                            inputState = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(TextBoxColor),
                        maxLines = 1,
                        textStyle = TextStyle.Default.copy(color = WhiteColor)
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    clickAction.invoke(inputState)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    DynamicText(
                        text = "Save",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
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
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(WhiteColor)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    EditText(
                        value = oldState,
                        onValueChange = {
                            oldState = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
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
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(WhiteColor)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    EditText(
                        value = inputState,
                        onValueChange = {
                            inputState = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    clickAction.invoke(inputState, oldState)
                }
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DynamicText(
                        text = "Save",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
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
fun LogoutPopup(
    yesAction: () -> Unit,
    noAction: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = "Logout")
        },
        text = {
            Box {
                Text(text = "Do you really want to logout?")
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
