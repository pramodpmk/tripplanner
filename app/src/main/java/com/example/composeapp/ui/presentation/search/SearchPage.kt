package com.example.composeapp.ui.presentation.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.composeapp.ui.components.CustomEditField
import com.example.composeapp.ui.components.DynamicText
import com.example.composeapp.ui.components.NonEditableField
import com.example.composeapp.ui.theme.ComposeAppTheme
import com.example.composeapp.ui.theme.ParentBgColor

@Composable
fun SearchPage(
    navHostController: NavHostController,
    searchBoxCallBack: (isExpanded: Boolean) -> Unit
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = ParentBgColor)
            .padding(8.dp)
    ) {
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
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                DynamicText(text = "Search")
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(16.dp))
            CustomEditField(
                label = "Location",
                onChangeAction = {

                },
                onNextAction = {

                },
                onDoneAction = {

                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            //Type of trip.

        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAppTheme {
        val navHostController = rememberNavController()
        SearchPage(
            navHostController = navHostController,
            searchBoxCallBack = {}
        )
    }
}