package com.example.composeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeapp.ui.theme.ComposeAppTheme

@Composable
fun SearchBox(
    placeHolder: String,
    onClick: () -> Unit
) {
    val text = remember {
      mutableStateOf("")
    }
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(
                color = Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp)
            .border(
                1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Text(
            text = placeHolder,
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .padding(16.dp)
                .clickable {
                    onClick.invoke()
                }
        )
    }
}

@Composable
fun SearchInputBox(
    placeHolder: String
) {
    val text = remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(
                color = Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp)
            .border(
                1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
//        TextField(
//            value = text.value,
//            onValueChange = {
//                text.value = it
//            },
//            placeholder = {
//                Text(text = placeHolder)
//            },
//            shape = RoundedCornerShape(12.dp),
//            modifier = Modifier
//                .background(Color.Transparent)
//                .fillMaxWidth(),
//            colors = TextFieldDefaults.textFieldColors(
//                backgroundColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                cursorColor = Color.Gray
//            )
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchBox() {
    ComposeAppTheme {
        SearchBox(placeHolder = "Type in ...", onClick = {})
    }
}