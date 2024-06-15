package com.example.composeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.composeapp.data.detail.DetailModel
import com.example.composeapp.ui.theme.ComposeAppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun LibraryTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    isCollapsed: Boolean
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        LargeTopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Library")
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = MaterialTheme.colorScheme.background,
                titleContentColor = if (isCollapsed) {
                    MaterialTheme.colorScheme.onBackground
                } else {
                    MaterialTheme.colorScheme.onPrimary
                },
            ),
            scrollBehavior = scrollBehavior,
            navigationIcon = {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
            },
            modifier = Modifier
                .background(color = Color.Transparent),
            actions = {
                GlideImage(
                    model = "https://res.cloudinary.com/dddd9bezd/image/upload/v1675228784/AiAgents/chatbot-100x100_a6ur0q.jpg",
                    contentDescription = "trip image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(300.dp)
                ) {
                    it.thumbnail()
                }
            }
        )
    }
}

@Composable
fun DetailContent(
    detailModel: DetailModel,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier.padding(
            start = 18.dp,
            end = 18.dp
        )
    ) {
        DynamicText(
            text = detailModel.title,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        DynamicText(
            text = detailModel.location,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(6.dp))
        DynamicText(
            text = detailModel.dayDesc,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        DynamicText(
            text = "About this place",
            style = MaterialTheme.typography.headlineSmall
        )
        DynamicText(
            text = detailModel.description,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(18.dp))
        detailModel.days.forEach { item ->
            DayContent(dayOfTrip = item)
        }
        if (detailModel.tips.isNotEmpty()) {
            Spacer(modifier = Modifier.height(18.dp))
            DynamicText(
                text = "Tips !",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(6.dp))
            val desc = StringBuilder()
            detailModel.tips.forEach { item ->
                desc.append("• $item")
                desc.append("\n")
            }
            DynamicText(
                text = desc.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(26.dp))

        }
        Spacer(modifier = Modifier.height(76.dp))
    }
}

@Composable
fun DayContent(
    dayOfTrip: DetailModel.DaysOfTrip
) {
    Column {
        DynamicText(
            text = dayOfTrip.dayName,
            style = MaterialTheme.typography.headlineMedium
        )
        if (dayOfTrip.morning.isNotEmpty()) {
            TimeOfDaySection(
                titleTime = "Morning",
                timeOfDay = dayOfTrip.morning
            )
        }
        if (dayOfTrip.afternoon.isNotEmpty()) {
            TimeOfDaySection(
                titleTime = "Afternoon",
                timeOfDay = dayOfTrip.afternoon
            )
        }
        if (dayOfTrip.evening.isNotEmpty()) {
            TimeOfDaySection(
                titleTime = "Evening",
                timeOfDay = dayOfTrip.evening
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun TimeOfDaySection(
    titleTime: String,
    timeOfDay: List<DetailModel.TimeOfDay>
) {
    DynamicText(
        text = titleTime,
        style = MaterialTheme.typography.headlineSmall
    )
    val desc = StringBuilder()
    timeOfDay.forEach { item ->
        desc.append("• ${item.description}")
        desc.append("\n")
    }
    DynamicText(
        text = desc.toString(),
        style = MaterialTheme.typography.bodyMedium
    )
    Spacer(modifier = Modifier.height(12.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAppTheme {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
            rememberTopAppBarState()
        )
        //val navHostController = rememberNavController()
        //HomeRoute(navHostController = navHostController)
        LibraryTopBar(
            scrollBehavior = scrollBehavior,
            isCollapsed = false
        )
    }
}
