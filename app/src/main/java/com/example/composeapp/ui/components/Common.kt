package com.example.composeapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.composeapp.R
import com.example.composeapp.ui.theme.ButtonColor
import com.example.composeapp.ui.theme.ChildBgColor
import com.example.composeapp.ui.theme.OffWhite
import com.example.composeapp.ui.theme.TextBoxColor
import com.example.composeapp.ui.theme.WhiteColor
import com.example.composeapp.utils.AdmobUtils
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

@Composable
fun DynamicText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    color: Color = Color.White,
    onClick: ((_text: String) -> Unit)? = null
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        overflow = overflow,
        color = color
    )
}

@Composable
fun NormalText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    color: Color = Color.White,
    onClick: ((_text: String) -> Unit)? = null
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        overflow = overflow,
        color = color
    )
}

@Composable
fun DynamicRow(
    textTitle: String,
    textDesc: String,
    imagePath: Int
) {

    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = imagePath),
                contentDescription = "thumbnail",
                modifier = Modifier.size(90.dp)
            )
            Column {
                Spacer(modifier = Modifier.height(20.dp))
                DynamicText(text = textTitle)
                DynamicText(text = textDesc)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
        )
    }
}

@Composable
fun PasswordInputField(
    text: String,
    isDoneEnabled: Boolean = false,
    focusRequester: FocusRequester? = null,
    onChangeAction: (input: String) -> Unit,
    onDoneAction: (input: String) -> Unit
) {
    var password by remember {
        mutableStateOf("")
    }
    val passwordFocusRequester = remember {
        FocusRequester()
    }
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .background(TextBoxColor, RoundedCornerShape(10.dp))
    ) {
        TextField(
            value = password,
            onValueChange = {
                password = it
                onChangeAction.invoke(password)
            },
            label = {
                Text(text, color = OffWhite)
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,

                ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .focusRequester(focusRequester ?: passwordFocusRequester),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = if (isDoneEnabled) {
                    ImeAction.Done
                } else {
                    ImeAction.Next
                }
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDoneAction.invoke(password)
                },
                onNext = {
                    onDoneAction.invoke(password)
                }
            ),
            visualTransformation = if (passwordVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = if (passwordVisibility) {
                            painterResource(id = R.drawable.ic_visibility_on)
                        } else {
                            painterResource(id = R.drawable.ic_visibility_off)
                        },
                        contentDescription = "visibility",
                        tint = WhiteColor
                    )
                }
            },
            textStyle = TextStyle.Default.copy(
                color = WhiteColor,
                fontSize = 16.sp
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default.copy(color = WhiteColor),
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    modifier
        .fillMaxWidth()
        .background(TextBoxColor)
    OutlinedTextField(
        value,
        onValueChange,
        modifier,
        enabled,
        readOnly,
        textStyle,
        label,
        placeholder,
        leadingIcon,
        trailingIcon
    )
}

@Composable
fun CustomEditField(
    label: String,
    textValue: String = "",
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    textStyle: TextStyle = TextStyle.Default.copy(color = WhiteColor),
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    focusRequester: FocusRequester? = null,
    isDoneEnabled: Boolean = false,
    onChangeAction: (input: String) -> Unit,
    onNextAction: (input: String) -> Unit,
    onDoneAction: (input: String) -> Unit
) {
    var text by remember {
        mutableStateOf(textValue)
    }
    val textFocusRequester = remember {
        FocusRequester()
    }
    Box(
        modifier = Modifier
            .background(TextBoxColor, RoundedCornerShape(10.dp))
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
                onChangeAction.invoke(it)
            },
            label = { Text(label, color = OffWhite) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,

                ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester ?: textFocusRequester)
                .padding(4.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = if (isDoneEnabled) {
                    ImeAction.Done
                } else {
                    ImeAction.Next
                }
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    onNextAction.invoke(text)
                },
                onDone = {
                    onDoneAction.invoke(text)
                }
            ),
            textStyle = TextStyle.Default.copy(
                color = WhiteColor,
                fontSize = 16.sp
            )
        )
    }
}

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit
) {
    Box {
        Button(
            onClick = {
                onClick.invoke()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColor
            )
        ) {
            Text(
                text.uppercase(),
                color = WhiteColor
            )
        }
    }
}

@Composable
fun CustomButton(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Button(
            onClick = {
                onClick.invoke()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColor
            )
        ) {
            Text(text.uppercase())
        }
    }
}

@Composable
fun AdmobBanner(
    modifier: Modifier = Modifier,
    adId: String
) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            // on below line specifying ad view.
            AdView(context).apply {
                // on below line specifying ad size
                //adSize = AdSize.BANNER
                // on below line specifying ad unit id
                // currently added a test ad unit id.
                setAdSize(AdSize.BANNER)
                adUnitId = adId
                // calling load ad to load our ad.
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}
